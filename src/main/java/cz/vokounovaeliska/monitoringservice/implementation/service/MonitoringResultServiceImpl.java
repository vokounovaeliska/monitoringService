package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.exception.ResourceNotFoundException;
import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoringResultRequest;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import cz.vokounovaeliska.monitoringservice.entity.MonitoringResult;
import cz.vokounovaeliska.monitoringservice.repository.MonitoringResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringResultServiceImpl implements MonitoringResultService {

    @Autowired
    private final MonitoringResultRepository repository;

    public MonitoringResultServiceImpl(MonitoringResultRepository repository) {
        this.repository = repository;
    }


    @Override
    public String addMonitoringResult(AddMonitoringResultRequest addMonitoringResultRequest) {
        //TODO
        return null;
    }

    @Override
    public void delete(long id) {
        //TODO
    }

    @Override
    public MonitoringResultDTO get(long id) {
        return repository.findById(id)
                .map(this::mapResultToResultDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Monitoring result ID: " + id + " not found."));
    }

    @Override
    public List<MonitoringResultDTO> getAll() {
        //TODO
        return null;
    }

    private MonitoringResultDTO mapResultToResultDTO(MonitoringResult monitoringResult) {
        return new MonitoringResultDTO(monitoringResult.getId(),
                monitoringResult.getDateOfCheck(),
                monitoringResult.getHttpStatusCode(),
                monitoringResult.getReturnedPayload(),
                monitoringResult.getMonitoredEndpoint().getId()
        );
    }
}
