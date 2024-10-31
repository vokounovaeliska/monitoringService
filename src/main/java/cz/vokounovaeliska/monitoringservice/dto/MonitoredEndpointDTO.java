package cz.vokounovaeliska.monitoringservice.dto;

import lombok.Value;
import java.time.OffsetDateTime;

@Value
public class MonitoredEndpointDTO {
    long id;
    String name;
    String url;
    OffsetDateTime dateOfCreation;
    OffsetDateTime dateOfLastCheck;
    OffsetDateTime monitoredInterval;
    long ownerId;
}
