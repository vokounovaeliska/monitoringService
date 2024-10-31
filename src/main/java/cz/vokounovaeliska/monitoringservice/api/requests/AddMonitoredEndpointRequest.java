package cz.vokounovaeliska.monitoringservice.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMonitoredEndpointRequest {
    private String name;
    private String url;
    private OffsetDateTime monitoredInterval;
}
