package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;

public interface MonitorService {
    void monitorEndpoint(MonitoredEndpointDTO endpoint);
    void removeScheduledTask(Long endpointId);
}
