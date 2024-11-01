package cz.vokounovaeliska.monitoringservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity(name = "monitored_endpoint")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MonitoredEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = true, unique = true)
    private String url;

    @Column(nullable = true)
    private OffsetDateTime dateOfCreation;

    @Column(nullable = true)
    private OffsetDateTime dateOfLastCheck;

    @Setter
    @Column(nullable = true)
    private Long monitoredInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public MonitoredEndpoint(String name, String url, Long monitoredInterval, User owner) {
        this.name = name;
        this.url = url;
        this.dateOfCreation = OffsetDateTime.now();
        this.dateOfLastCheck = OffsetDateTime.now();
        this.monitoredInterval = monitoredInterval;
        this.owner = owner;
    }
}
