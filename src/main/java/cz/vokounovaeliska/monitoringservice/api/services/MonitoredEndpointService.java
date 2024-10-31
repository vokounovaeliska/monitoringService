package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;

import java.util.List;

public interface MonitoredEndpointService {
    String addMonitoredEndpoint(AddMonitoredEndpointRequest addMonitoredEndpointRequest);

    void delete(long id);

    MonitoredEndpointDTO get(long id);

    List<MonitoredEndpointDTO> getAll();
}
