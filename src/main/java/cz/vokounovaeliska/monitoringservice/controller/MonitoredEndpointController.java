package cz.vokounovaeliska.monitoringservice.controller;


import cz.vokounovaeliska.monitoringservice.api.requests.AddMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.requests.EditMonitoredEndpointRequest;
import cz.vokounovaeliska.monitoringservice.api.services.MonitoredEndpointService;
import cz.vokounovaeliska.monitoringservice.dto.MonitoredEndpointDTO;
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


    @GetMapping
    public ResponseEntity<List<MonitoredEndpointDTO>> getAll() {
        return ResponseEntity.ok().body(monitoredEndpointService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<MonitoredEndpointDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(monitoredEndpointService.get(id));
    }

    @PostMapping()
    public ResponseEntity<Long> add(@Valid @RequestBody AddMonitoredEndpointRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monitoredEndpointService.add(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<Long> edit(@PathVariable("id") long id, @Valid @RequestBody EditMonitoredEndpointRequest request) {
        return ResponseEntity.ok().body(monitoredEndpointService.edit(id, request));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<MonitoredEndpointDTO>> getByOwnerId(@PathVariable("ownerId") long ownerId) {
        return ResponseEntity.ok().body(monitoredEndpointService.getAllByOwner(ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        monitoredEndpointService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
