package cz.vokounovaeliska.monitoringservice.entity;

import cz.vokounovaeliska.monitoringservice.implementation.service.MonitoredEndpointServiceImpl;
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

    @Column(nullable = true)
    private OffsetDateTime dateOfCheck;

    @Column(nullable = true)
    private Integer httpStatusCode;

    @Column(nullable = true)
    private String returnedPayload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitored_endpoint_id", nullable = false)
    private MonitoredEndpoint monitoredEndpoint;

    public MonitoringResult(Integer httpStatusCode, String returnedPayload, MonitoredEndpoint monitoredEndpoint) {
        this.dateOfCheck = OffsetDateTime.now();
        this.httpStatusCode = httpStatusCode;
        this.returnedPayload = returnedPayload;
        this.monitoredEndpoint = monitoredEndpoint;
    }
}
