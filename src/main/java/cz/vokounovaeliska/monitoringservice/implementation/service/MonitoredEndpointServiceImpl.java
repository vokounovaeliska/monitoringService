package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.exception.BadRequestException;
import cz.vokounovaeliska.monitoringservice.api.exception.InternalErrorException;
import cz.vokounovaeliska.monitoringservice.api.exception.ResourceNotFoundException;
import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.requests.EditMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.api.services.UserService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import cz.vokounovaeliska.monitoringservice.dto.UserDTO;
import cz.vokounovaeliska.monitoringservice.entity.MonitoredEndpoint;
import cz.vokounovaeliska.monitoringservice.entity.User;
import cz.vokounovaeliska.monitoringservice.repository.MonitoredEndpointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class MonitoredEndpointServiceImpl implements MonitoredEndpointService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoredEndpointServiceImpl.class);


    private final MonitoredEndpointRepository repository;

    public MonitoredEndpointServiceImpl(MonitoredEndpointRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    private final UserService userService;


    @Override
    public Long add(AddMonitoredEndpointRequest addMonitoredEndpointRequest) {
        try {
            final UserDTO userDTO = userService.get(addMonitoredEndpointRequest.getOwner_id());
            final User user = new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getAccessToken());

            MonitoredEndpoint endpoint = new MonitoredEndpoint(
                    addMonitoredEndpointRequest.getName(),
                    addMonitoredEndpointRequest.getUrl(),
                    addMonitoredEndpointRequest.getMonitoredInterval(),
                    user
            );
            return repository.save(endpoint).getId();
        } catch (DataIntegrityViolationException e) {
            LOG.error("Data integrity violation while adding endpoint: {}", e.getMessage());
            throw new BadRequestException("Endpoint with url: " + addMonitoredEndpointRequest.getUrl() + " already exists");
        } catch (DataAccessException e) {
            LOG.error("Error while adding endpoint: {}", e.getMessage());
            throw new InternalErrorException("Error while adding endpoint");
        } catch (Exception e) {
            LOG.error("Unexpected error while adding endpoint: {}", e.getMessage());
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
    public Long edit(long id, EditMonitoredEndpointRequest request) {
        final MonitoredEndpoint monitoredEndpoint = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endpoint ID: " + id + " not found."));
        monitoredEndpoint.setName(request.getName());
        monitoredEndpoint.setUrl(request.getUrl());
        monitoredEndpoint.setMonitoredInterval(request.getMonitoredInterval());
        repository.save(monitoredEndpoint).getId();
        return null;
    }

    @Override
    public MonitoredEndpointDTO get(long id) {
        return repository.findById(id)
                .map(this::mapEndpointToEndpointDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Endpoint ID: " + id + " not found."));
    }

    @Override
    public List<MonitoredEndpointDTO> getAll() {
        return repository.findAll().stream().map(this::mapEndpointToEndpointDTO).collect(Collectors.toList());
    }

    @Override
    public List<MonitoredEndpointDTO> getAllByOwner(long ownerId) {
        if (userService.get(ownerId) != null) {
            return repository.findAllByOwnerId(ownerId).stream()
                    .map(this::mapEndpointToEndpointDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private MonitoredEndpointDTO mapEndpointToEndpointDTO(MonitoredEndpoint monitoredEndpoint) {
        return new MonitoredEndpointDTO(monitoredEndpoint.getId(), monitoredEndpoint.getName(), monitoredEndpoint.getUrl(), monitoredEndpoint.getDateOfCreation(), monitoredEndpoint.getDateOfLastCheck(), monitoredEndpoint.getMonitoredInterval(), monitoredEndpoint.getOwner().getId());
    }
}
