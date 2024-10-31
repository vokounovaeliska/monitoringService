package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoringResultRequest;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;

import java.util.List;

public interface MonitoringResultService {

    String addMonitoringResult(AddMonitoringResultRequest addMonitoringResultRequest);

    void delete(long id);

    MonitoringResultDTO get(long id);

    List<MonitoringResultDTO> getAll();
}
