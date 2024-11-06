package cz.vokounovaeliska.monitoringservice.controller;

import cz.vokounovaeliska.monitoringservice.api.services.MonitoringResultService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoringResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get a monitoring result by ID")
    @GetMapping("{id}")
    public ResponseEntity<MonitoringResultDTO> getById(
            @Parameter(description = "ID of the monitoring result to retrieve", required = true)
            @PathVariable("id") long id) {
        return ResponseEntity.ok().body(monitoringResultService.get(id));
    }

    @Operation(summary = "Get the last 10 monitoring results for a specific monitored endpoint")
    @GetMapping("endpoint/{endpointId}")
    public ResponseEntity<List<MonitoringResultDTO>> getLast10ByEndpointId(
            @Parameter(description = "ID of the monitored endpoint to get results for", required = true)
            @PathVariable("endpointId") long endpointId) {
        return ResponseEntity.ok().body(monitoringResultService.getLast10ByMonitoredEndpointId(endpointId));
    }

    @Operation(summary = "Get all monitoring results")
    @GetMapping()
    public ResponseEntity<List<MonitoringResultDTO>> getAll() {
        return ResponseEntity.ok().body(monitoringResultService.getAll());
    }
}
