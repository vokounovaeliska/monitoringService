package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;

import java.util.List;

public interface MonitoringResultService {

    Long add(MonitoringResultDTO monitoringResultDTO);

    void delete(long id);

    MonitoringResultDTO get(long id);

    List<MonitoringResultDTO> getAll();

    List<MonitoringResultDTO> getLast10ByMonitoredEndpointId(Long monitoredEndpointId);

}
