package cz.vokounovaeliska.monitoringservice.dto;

import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class MonitoringResultDTO {
    Long id;
    OffsetDateTime dateOfCheck;
    Integer httpStatusCode;
    String returnedPayload;
    String reasonPhrase;
    long monitoredEndpointId;

    public MonitoringResultDTO(Integer httpStatusCode, String returnedPayload, String reasonPhrase, long monitoredEndpointId) {
        this.dateOfCheck = OffsetDateTime.now();
        this.httpStatusCode = httpStatusCode;
        this.returnedPayload = returnedPayload;
        this.reasonPhrase = reasonPhrase;
        this.monitoredEndpointId = monitoredEndpointId;
        id = null;
    }

    public MonitoringResultDTO(long id, OffsetDateTime dateOfCheck, Integer httpStatusCode, String returnedPayload, String reasonPhrase, long monitoredEndpointId) {
        this.id = id;
        this.dateOfCheck = dateOfCheck;
        this.httpStatusCode = httpStatusCode;
        this.returnedPayload = returnedPayload;
        this.reasonPhrase = reasonPhrase;
        this.monitoredEndpointId = monitoredEndpointId;
    }
}
