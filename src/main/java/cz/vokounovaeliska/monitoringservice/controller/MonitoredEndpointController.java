package cz.vokounovaeliska.monitoringservice.controller;

import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.requests.EditMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitoredEndpoints")
public class MonitoredEndpointController {

    private final MonitoredEndpointService monitoredEndpointService;

    public MonitoredEndpointController(MonitoredEndpointService monitoredEndpointService) {
        this.monitoredEndpointService = monitoredEndpointService;
    }

    @Operation(summary = "Get all monitored endpoints")
    @GetMapping
    public ResponseEntity<List<MonitoredEndpointDTO>> getAll() {
        return ResponseEntity.ok().body(monitoredEndpointService.getAll());
    }

    @Operation(summary = "Get a monitored endpoint by ID")
    @GetMapping("{id}")
    public ResponseEntity<MonitoredEndpointDTO> getById(
            @Parameter(description = "ID of the monitored endpoint to retrieve")
            @PathVariable("id") long id) {
        return ResponseEntity.ok().body(monitoredEndpointService.get(id));
    }

    @Operation(summary = "Create a new monitored endpoint")
    @PostMapping
    public ResponseEntity<Long> add(
            @RequestBody @Valid
            @Parameter(description = "Details of the monitored endpoint to add", required = true)
            AddMonitoredEndpointRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monitoredEndpointService.add(request));
    }

    @Operation(summary = "Update an existing monitored endpoint")
    @PutMapping("{id}")
    public ResponseEntity<Long> edit(
            @Parameter(description = "ID of the monitored endpoint to update")
            @PathVariable("id") long id,
            @Valid @RequestBody
            @Parameter(description = "Updated details of the monitored endpoint", required = true)
            EditMonitoredEndpointRequest request) {
        return ResponseEntity.ok().body(monitoredEndpointService.edit(id, request));
    }

    @Operation(summary = "Delete a monitored endpoint by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the monitored endpoint to delete")
            @PathVariable("id") long id) {
        monitoredEndpointService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all monitored endpoints for a specific user")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<MonitoredEndpointDTO>> getByOwnerId(
            @Parameter(description = "Owner ID to filter monitored endpoints by")
            @PathVariable("ownerId") long ownerId) {
        return ResponseEntity.ok().body(monitoredEndpointService.getAllByOwner(ownerId));
    }
}
