package cz.vokounovaeliska.monitoringservice.controller;


import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoringResultRequest;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import org.hibernate.boot.spi.AdditionalJaxbMappingProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitoringResults")
public class MonitoringResultController {

    private final MonitoringResultService monitoringResultService;

    public MonitoringResultController(MonitoringResultService monitoringResultService) {
        this.monitoringResultService = monitoringResultService;
    }

    @GetMapping("{id}")
    public ResponseEntity<MonitoringResultDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(monitoringResultService.get(id));
    }

    @GetMapping("endpoint/{endpointId}")
    public ResponseEntity<List<MonitoringResultDTO>> getLast10ByEndpointId(@PathVariable("endpointId") long endpointId) {
        return ResponseEntity.ok().body(monitoringResultService.getLast10ByMonitoredEndpointId(endpointId));
    }

    @GetMapping()
    public ResponseEntity<List<MonitoringResultDTO>> getAll() {
        return ResponseEntity.ok().body(monitoringResultService.getAll());
    }
}
