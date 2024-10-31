package cz.vokounovaeliska.monitoringservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity(name = "monitored_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private OffsetDateTime dateOfCheck;

    @Column(nullable = true)
    private Integer httpStatusCode;

    @Column(nullable = true)
    private String returnedPayload;

    @ManyToOne
    @JoinColumn(name = "monitored_endpoint_id", nullable = false)
    private MonitoredEndpoint monitoredEndpoint;
}
