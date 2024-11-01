package cz.vokounovaeliska.monitoringservice.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMonitoredEndpointRequest {
    private String name;
    private String url;
    private Long monitoredInterval;
    private Long owner_id;
}
