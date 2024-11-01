package cz.vokounovaeliska.monitoringservice.controller;


import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
