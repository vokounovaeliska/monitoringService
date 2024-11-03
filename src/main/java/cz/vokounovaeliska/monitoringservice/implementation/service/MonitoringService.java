package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Service
public class MonitoringService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringService.class);

    private final MonitoredEndpointService monitoredEndpointService;

    private final MonitoringResultService monitoringResultService;

    public MonitoringService(MonitoredEndpointService monitoredEndpointService, MonitoringResultService monitoringResultService) {
        this.monitoredEndpointService = monitoredEndpointService;
        this.monitoringResultService = monitoringResultService;
    }

    // Run the monitoring every minute (adjust the interval as needed)
    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    public void monitorEndpoints() {
        var monitoredEndpoints = monitoredEndpointService.getAll();

        RequestConfig requestConfig = RequestConfig.custom()
                .setResponseTimeout(Timeout.ofSeconds(10))
                .setConnectionRequestTimeout(Timeout.ofSeconds(3))
                .build();

        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {

            for (var endpoint : monitoredEndpoints) {
                String resultContent = "";
                HttpGet httpGet = new HttpGet(endpoint.getUrl());


                try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                    int statusCode = response.getCode();
                    //The returned content is too big
                    //HttpEntity entity = response.getEntity();
                    //resultContent = response.getEntity().getContent().toString();
                    String reasonPhrase = response.getReasonPhrase();
                    MonitoringResultDTO resultDTO = new MonitoringResultDTO(statusCode, resultContent, reasonPhrase, endpoint.getId());
                    monitoringResultService.add(resultDTO);
                    monitoredEndpointService.updateDateOfLastCheck(endpoint.getId());

                } catch (SocketTimeoutException e) {
                    LOG.error("Connection timeout for URL: {}", endpoint.getUrl());

                    // Save timeout result with HTTP 408 status code
                    MonitoringResultDTO timeoutResultDTO = new MonitoringResultDTO(408,
                            resultContent, "Request timeout (408)", endpoint.getId());
                    monitoringResultService.add(timeoutResultDTO);

                } catch (IOException e) {
                    LOG.error("Error monitoring URL: {} - {}", endpoint.getUrl(), e.getMessage());

                    // Save general error result
                    MonitoringResultDTO errorResultDTO = new MonitoringResultDTO(-1,
                            resultContent, e.getMessage(), endpoint.getId());
                    monitoringResultService.add(errorResultDTO);
                }
            }
        } catch (IOException e) {
            LOG.error("Error creating HTTP client: {}", e.getMessage());
        }
    }
}
