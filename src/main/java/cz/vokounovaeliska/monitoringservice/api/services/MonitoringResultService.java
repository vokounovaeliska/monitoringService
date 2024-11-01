package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;

import java.util.List;

public interface MonitoringResultService {

//    Long add(AddMonitoringResultRequest addMonitoringResultRequest);
//
//    void delete(long id);

    MonitoringResultDTO get(long id);

    List<MonitoringResultDTO> getAll();

    List<MonitoringResultDTO> getLast10ByMonitoredEndpointId(Long monitoredEndpointId);

}
