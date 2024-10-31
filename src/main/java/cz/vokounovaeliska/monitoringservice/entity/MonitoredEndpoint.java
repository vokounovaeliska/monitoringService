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
@Setter
public class MonitoredEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String url;

    @Column(nullable = true)
    private OffsetDateTime dateOfCreation;

    @Column(nullable = true)
    private OffsetDateTime dateOfLastCheck;

    @Column(nullable = true)
    private Long monitoredInterval;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}
