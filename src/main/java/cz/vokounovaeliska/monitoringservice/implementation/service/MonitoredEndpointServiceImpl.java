package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import cz.vokounovaeliska.monitoringservice.repository.MonitoredEndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MonitoredEndpointServiceImpl implements MonitoredEndpointService {

    @Autowired
    private final MonitoredEndpointRepository monitoredEndpointRepository;

    public MonitoredEndpointServiceImpl(MonitoredEndpointRepository monitoredEndpointRepository) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    @Override
    public String addMonitoredEndpoint(AddMonitoredEndpointRequest addMonitoredEndpointRequest) {
        //TODO
        return null;
    }

    @Override
    public void delete(long id) {
        //TODO
    }

    @Override
    public MonitoredEndpointDTO get(long id) {
        //TODO
        return null;
    }

    @Override
    public List<MonitoredEndpointDTO> getAll() {
        //TODO
        return null;
    }
}
