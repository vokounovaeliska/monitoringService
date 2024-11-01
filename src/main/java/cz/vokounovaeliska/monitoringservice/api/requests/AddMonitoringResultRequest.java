package cz.vokounovaeliska.monitoringservice.api.requests;

import cz.vokounovaeliska.monitoringservice.entity.MonitoredEndpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMonitoringResultRequest {
    private OffsetDateTime dateOfCheck;
    private Integer httpStatusCode;
    private String returnedPayload;
    private MonitoredEndpoint monitoredEndpointId;
}
