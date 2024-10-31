package cz.vokounovaeliska.monitoringservice.dto;

import lombok.Value;

import java.time.OffsetDateTime;
@Value
public class MonitoringResultDTO {
    long id;
    OffsetDateTime dateOfCheck;
    Integer httpStatusCode;
    String returnedPayload;
    long monitoredEndpointId;
}
