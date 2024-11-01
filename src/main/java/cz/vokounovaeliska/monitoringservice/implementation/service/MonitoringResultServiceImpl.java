package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.exception.ResourceNotFoundException;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import cz.vokounovaeliska.monitoringservice.entity.MonitoringResult;
import cz.vokounovaeliska.monitoringservice.repository.MonitoringResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoringResultServiceImpl implements MonitoringResultService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringResultServiceImpl.class);

    @Autowired
    private final MonitoringResultRepository repository;

    public MonitoringResultServiceImpl(MonitoringResultRepository repository) {
        this.repository = repository;
    }


   /* @Override
    public Long add(AddMonitoringResultRequest addMonitoringResultRequest) {
        try {
            return repository.save(new MonitoringResult(addMonitoringResultRequest.hashCode(), addMonitoringResultRequest.getReturnedPayload(), addMonitoringResultRequest.getMonitoredEndpointId())).getId();
        } catch (DataAccessException e) {
            LOG.error("Error while adding endpoint", e);
            throw new InternalErrorException("Error while adding endpoint");
        }
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            repository.deleteById(id);
        }
    }*/

    @Override
    public MonitoringResultDTO get(long id) {
        return repository.findById(id).map(this::mapResultToResultDTO).orElseThrow(() -> new ResourceNotFoundException("Monitoring result ID: " + id + " not found."));
    }

    @Override
    public List<MonitoringResultDTO> getAll() {
        return repository.findAll().stream().map(this::mapResultToResultDTO).collect(Collectors.toList());
    }

    @Override
    public List<MonitoringResultDTO> getLast10ByMonitoredEndpointId(Long monitoredEndpointId) {
        return repository.findByMonitoredEndpointIdOrderByDateOfCheckDesc(monitoredEndpointId).stream()
                .limit(10)
                .map(this::mapResultToResultDTO)
                .collect(Collectors.toList());
    }


    private MonitoringResultDTO mapResultToResultDTO(MonitoringResult monitoringResult) {
        return new MonitoringResultDTO(monitoringResult.getId(),
                monitoringResult.getDateOfCheck(),
                monitoringResult.getHttpStatusCode(),
                monitoringResult.getReturnedPayload(),
                monitoringResult.getMonitoredEndpoint().getId());
    }
}
