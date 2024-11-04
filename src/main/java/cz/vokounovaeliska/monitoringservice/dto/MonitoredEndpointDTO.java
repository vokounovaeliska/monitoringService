package cz.vokounovaeliska.monitoringservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class MonitoredEndpointDTO {
    long id;
    String name;

    @NotNull(message = "URL cannot be null")
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
            message = "Invalid URL format")
    String url;
    OffsetDateTime dateOfCreation;
    OffsetDateTime dateOfLastCheck;
    Long monitoredInterval;
    long ownerId;
}
