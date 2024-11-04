package cz.vokounovaeliska.monitoringservice.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditMonitoredEndpointRequest {
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "URL cannot be null")
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
            message = "Invalid URL format")
    private String url;

    @NotNull(message = "Monitored interval cannot be null")
    @Positive(message = "Monitored interval must be a positive number")
    private Long monitoredInterval;
}
