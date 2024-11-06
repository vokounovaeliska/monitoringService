package cz.vokounovaeliska.monitoringservice;

import cz.vokounovaeliska.monitoringservice.api.exception.BadRequestException;
import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
@Transactional
public class EndpointIntegrationTests extends IntegrationTests {

    @Test
    public void getAll() {
        final ResponseEntity<List<MonitoredEndpointDTO>> responseEntity = restTemplate.exchange("/monitoredEndpoints",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        System.out.println(responseEntity.getBody());
        Assertions.assertTrue(responseEntity.getBody().size() >= 2);
    }

    @Test
    public void get() {
        AddMonitoredEndpointRequest request = generateRandomEndpoint();
        final long id = insertEndpoint(request);
        final ResponseEntity<MonitoredEndpointDTO> responseEntity = restTemplate.getForEntity("/monitoredEndpoints/" + id,
                MonitoredEndpointDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        System.out.println(responseEntity.getBody());
        Assertions.assertEquals(responseEntity.getBody().getName(), request.getName());
        Assertions.assertEquals(responseEntity.getBody().getUrl(), request.getUrl());
        Assertions.assertEquals(responseEntity.getBody().getMonitoredInterval(), request.getMonitoredInterval());
        Assertions.assertEquals(responseEntity.getBody().getOwnerId(), request.getOwner_id());
    }

    @Test
    public void insert() {
        insertEndpoint(generateRandomEndpoint());
    }

    @Test
    public void delete() {
        AddMonitoredEndpointRequest request = generateRandomEndpoint();
        final long id = insertEndpoint(request);
        final ResponseEntity<Void> responseEntity = restTemplate.exchange("/monitoredEndpoints/" + id,
                HttpMethod.DELETE,
                null,
                Void.class);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        final ResponseEntity<MonitoredEndpointDTO> responseEntity2 = restTemplate.getForEntity("/monitoredEndpoints/" + id,
                MonitoredEndpointDTO.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    @Test
    public void insertUrlAlreadyExists() {
        AddMonitoredEndpointRequest request = generateRandomEndpoint();
        insertEndpoint(request);
        final ResponseEntity<BadRequestException> responseEntity = restTemplate.postForEntity("/monitoredEndpoints",
                request,
                BadRequestException.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


    private AddMonitoredEndpointRequest generateRandomEndpoint() {
        return new AddMonitoredEndpointRequest("name" + Math.random(),
                "http://www.web" + Math.random() + ".com",
                (long) (Math.random() + 10), 1L);
    }

    private long insertEndpoint(AddMonitoredEndpointRequest request) {
        final ResponseEntity<Long> responseEntity = restTemplate.postForEntity("/monitoredEndpoints",
                request,
                Long.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        return responseEntity.getBody();

    }
}
