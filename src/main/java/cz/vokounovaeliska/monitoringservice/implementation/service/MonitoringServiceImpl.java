package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.services.MonitorService;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class MonitoringServiceImpl implements MonitorService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringServiceImpl.class);
    private final MonitoredEndpointService monitoredEndpointService;
    private final MonitoringResultService monitoringResultService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    public MonitoringServiceImpl(MonitoredEndpointService monitoredEndpointService, MonitoringResultService monitoringResultService) {
        this.monitoredEndpointService = monitoredEndpointService;
        this.monitoringResultService = monitoringResultService;
        scheduleMonitoringTasks();
    }

    private void scheduleMonitoringTasks() {
        List<MonitoredEndpointDTO> monitoredEndpoints = monitoredEndpointService.getAll();
        if (monitoredEndpoints != null) {
            for (MonitoredEndpointDTO endpoint : monitoredEndpoints) {
                long interval = endpoint.getMonitoredInterval();
                if (!scheduledTasks.containsKey(endpoint.getId()) && interval > 0) {
                    ScheduledFuture<?> newTask = scheduler.scheduleAtFixedRate(() -> monitorEndpoint(endpoint), 0, interval, TimeUnit.SECONDS);
                    scheduledTasks.put(endpoint.getId(), newTask);
                } else {
                    LOG.warn("Skipping endpoint {} with non-positive interval or already added endpoint: {}", endpoint.getUrl(), interval);
                }
            }
        } else {
            LOG.error("Monitored endpoints list is null");
        }
    }

    @Override
    @Async
    public void monitorEndpoint(MonitoredEndpointDTO endpoint) {
        RequestConfig requestConfig = RequestConfig.custom().setResponseTimeout(Timeout.ofSeconds(3)).setConnectionRequestTimeout(Timeout.ofSeconds(3)).build();

        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            //Set default resultContent to blank string, because the payload is too big.
            String resultContent = "";
            HttpGet httpGet = new HttpGet(endpoint.getUrl());
            httpGet.setHeader("User-Agent", "MyCustomClient/1.0");


            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                int statusCode = response.getCode();
                String reasonPhrase = response.getReasonPhrase();
                //resultContent = EntityUtils.toString(response.getEntity(), "UTF-8");

                MonitoringResultDTO resultDTO = new MonitoringResultDTO(statusCode, resultContent, reasonPhrase, endpoint.getId());
                monitoringResultService.add(resultDTO);

//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
            } catch (SocketTimeoutException e) {
                LOG.error("Connection timeout for URL: {}", endpoint.getUrl());
                MonitoringResultDTO timeoutResultDTO = new MonitoringResultDTO(408, e.getMessage(), "Request timeout (408)", endpoint.getId());
                monitoringResultService.add(timeoutResultDTO);
            } catch (Exception e) {
                String cause = (e.getCause() != null) ? e.getCause().toString() : "No cause available";
                MonitoringResultDTO errorResultDTO = new MonitoringResultDTO(-1, cause, e.getMessage(), endpoint.getId());
                monitoringResultService.add(errorResultDTO);
            } finally {
                monitoredEndpointService.updateDateOfLastCheck(endpoint.getId());
            }
        } catch (IOException e) {
            LOG.error("Error creating HTTP client: {}", e.getMessage());
        }
    }

    @Override
    public void removeScheduledTask(Long endpointId) {
        LOG.info("Removing Scheduled task ID: {}", endpointId);
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(endpointId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledTasks.remove(endpointId);
        }
        scheduleMonitoringTasks();
    }

}
