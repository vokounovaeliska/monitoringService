package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.exception.InternalErrorException;
import cz.vokounovaeliska.monitoringservice.api.exception.ResourceNotFoundException;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import cz.vokounovaeliska.monitoringservice.entity.MonitoredEndpoint;
import cz.vokounovaeliska.monitoringservice.entity.MonitoringResult;
import cz.vokounovaeliska.monitoringservice.repository.MonitoringResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoringResultServiceImpl implements MonitoringResultService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringResultServiceImpl.class);

    private final MonitoringResultRepository repository;

    private final MonitoredEndpointService monitoredEndpointService;

    public MonitoringResultServiceImpl(MonitoringResultRepository repository, MonitoredEndpointService monitoredEndpointService) {
        this.repository = repository;
        this.monitoredEndpointService = monitoredEndpointService;
    }

    @Override
    public Long add(MonitoringResultDTO monitoringResultDTO) {
        try {
            final MonitoredEndpoint monitoredEndpoint = monitoredEndpointService.getEntityById(monitoringResultDTO.getMonitoredEndpointId());

            MonitoringResult monitoringResult = new MonitoringResult(
                    monitoringResultDTO.getHttpStatusCode(),
                    monitoringResultDTO.getReturnedPayload(),
                    monitoringResultDTO.getReasonPhrase(),
                    monitoredEndpoint
            );
            return repository.save(monitoringResult).getId();
        } catch (DataAccessException e) {
            LOG.error("Error while adding monitoring result: {}", e.getMessage());
            throw new InternalErrorException("Error while adding monitoring result");
        } catch (Exception e) {
            LOG.error("Unexpected error while adding monitoring result: {}", e.getMessage());
            throw new InternalErrorException("Unexpected error occurred");
        }
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            repository.deleteById(id);
        }
    }

    @Override
    public MonitoringResultDTO get(long id) {
        return repository.findById(id)
                .map(this::mapResultToResultDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Monitoring result ID: " + id + " not found."));
    }

    @Override
    public List<MonitoringResultDTO> getAll() {
        return repository.findAllByOrderByDateOfCheckDesc()
                .stream()
                .limit(500)
                .map(this::mapResultToResultDTO).collect(Collectors.toList());
    }

    @Override
    public List<MonitoringResultDTO> getLast10ByMonitoredEndpointId(Long monitoredEndpointId) {
        return repository.findByMonitoredEndpointIdOrderByDateOfCheckDesc(monitoredEndpointId).stream()
                .limit(10)
                .map(this::mapResultToResultDTO)
                .collect(Collectors.toList());
    }


    private MonitoringResultDTO mapResultToResultDTO(MonitoringResult monitoringResult) {
        return new MonitoringResultDTO(
                monitoringResult.getId(),
                monitoringResult.getDateOfCheck(),
                monitoringResult.getHttpStatusCode(),
                monitoringResult.getReturnedPayload(),
                monitoringResult.getReasonPhrase(),
                monitoringResult.getMonitoredEndpoint().getId());
    }
}
