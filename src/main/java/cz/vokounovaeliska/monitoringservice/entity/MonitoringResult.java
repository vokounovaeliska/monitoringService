package cz.vokounovaeliska.monitoringservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity(name = "monitoring_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = true)
    private OffsetDateTime dateOfCheck;

    @Setter
    @Column(nullable = true)
    private Integer httpStatusCode;

    @Setter
    @Column(nullable = true)
    private String reasonPhrase;

    @Setter
    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String returnedPayload;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "monitored_endpoint_id", nullable = false)
    private MonitoredEndpoint monitoredEndpoint;

    public MonitoringResult(Integer httpStatusCode, String returnedPayload, String reasonPhrase, MonitoredEndpoint monitoredEndpoint) {
        this.dateOfCheck = OffsetDateTime.now();
        this.httpStatusCode = httpStatusCode;
        this.returnedPayload = returnedPayload;
        this.reasonPhrase = reasonPhrase;
        this.monitoredEndpoint = monitoredEndpoint;
    }
}
