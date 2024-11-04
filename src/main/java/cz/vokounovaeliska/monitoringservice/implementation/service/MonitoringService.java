package cz.vokounovaeliska.monitoringservice.implementation.service;

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
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MonitoringService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringService.class);

    private final MonitoredEndpointService monitoredEndpointService;
    private final MonitoringResultService monitoringResultService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public MonitoringService(MonitoredEndpointService monitoredEndpointService, MonitoringResultService monitoringResultService) {
        this.monitoredEndpointService = monitoredEndpointService;
        this.monitoringResultService = monitoringResultService;
        scheduleMonitoringTasks();
    }

    private void scheduleMonitoringTasks() {
        List<MonitoredEndpointDTO> monitoredEndpoints = monitoredEndpointService.getAll();

        if (monitoredEndpoints != null) {
            for (MonitoredEndpointDTO endpoint : monitoredEndpoints) {
                long interval = endpoint.getMonitoredInterval();
                if (interval > 0) {
                    scheduler.scheduleAtFixedRate(() -> monitorEndpoint(endpoint), 0, interval, TimeUnit.SECONDS);
                } else {
                    LOG.warn("Skipping endpoint {} with non-positive interval: {}", endpoint.getUrl(), interval);
                }
            }
        } else {
            LOG.error("Monitored endpoints list is null");
        }
    }

    @Async
    public void monitorEndpoint(MonitoredEndpointDTO endpoint) {
        RequestConfig requestConfig = RequestConfig.custom().setResponseTimeout(Timeout.ofSeconds(3)).setConnectionRequestTimeout(Timeout.ofSeconds(3)).build();

        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            //Set default resultContent to blank string, because the payload is too big.
            String resultContent = "";
            HttpGet httpGet = new HttpGet(endpoint.getUrl());

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                int statusCode = response.getCode();
                String reasonPhrase = response.getReasonPhrase();

                MonitoringResultDTO resultDTO = new MonitoringResultDTO(statusCode, resultContent, reasonPhrase, endpoint.getId());
                monitoringResultService.add(resultDTO);

            } catch (SocketTimeoutException e) {
                LOG.error("Connection timeout for URL: {}", endpoint.getUrl());
                MonitoringResultDTO timeoutResultDTO = new MonitoringResultDTO(408, resultContent, "Request timeout (408)", endpoint.getId());
                monitoringResultService.add(timeoutResultDTO);
            } catch (IOException e) {
                LOG.error("Error monitoring URL: {} - {}", endpoint.getUrl(), e.getMessage());
                MonitoringResultDTO errorResultDTO = new MonitoringResultDTO(-1, resultContent, e.getMessage(), endpoint.getId());
                monitoringResultService.add(errorResultDTO);
            } finally {
                monitoredEndpointService.updateDateOfLastCheck(endpoint.getId());
            }
        } catch (IOException e) {
            LOG.error("Error creating HTTP client: {}", e.getMessage());
        }
    }

    public void addMonitoringTask(MonitoredEndpointDTO endpoint) {
        System.out.println("added to monitor");
        long interval = endpoint.getMonitoredInterval();
        if (interval > 0) {
            scheduler.scheduleAtFixedRate(() -> monitorEndpoint(endpoint), 0, interval, TimeUnit.SECONDS);
            LOG.info("Scheduled monitoring task for endpoint: {}", endpoint.getUrl());
        } else {
            LOG.warn("Skipping endpoint {} with non-positive interval: {}", endpoint.getUrl(), interval);
        }
    }
}
