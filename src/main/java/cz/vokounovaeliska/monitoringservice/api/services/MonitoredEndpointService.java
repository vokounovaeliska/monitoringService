package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.requests.EditMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import cz.vokounovaeliska.monitoringservice.entity.MonitoredEndpoint;

import java.util.List;

public interface MonitoredEndpointService {
    Long add(AddMonitoredEndpointRequest addMonitoredEndpointRequest);

    void delete(long id);

    Long edit(long id, EditMonitoredEndpointRequest request);

    MonitoredEndpointDTO get(long id);

    List<MonitoredEndpointDTO> getAll();

    List<MonitoredEndpointDTO> getAllByOwner(long ownerId);

    Long updateDateOfLastCheck(long endpointId);

    MonitoredEndpoint getEntityById(Long id);

}
