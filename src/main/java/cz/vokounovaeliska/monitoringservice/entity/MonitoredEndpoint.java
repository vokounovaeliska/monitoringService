package cz.vokounovaeliska.monitoringservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

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
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
            message = "Invalid URL format")
    private String url;

    @Column(nullable = true)
    private OffsetDateTime dateOfCreation;

    @Setter
    @Column(nullable = true)
    private OffsetDateTime dateOfLastCheck;

    @Setter
    @Column(nullable = true)
    private Long monitoredInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "monitoredEndpoint", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MonitoringResult> monitoringResults;

    public MonitoredEndpoint(String name, String url, Long monitoredInterval, User owner) {
        this.name = name;
        this.url = url;
        this.dateOfCreation = OffsetDateTime.now();
        this.dateOfLastCheck = null;
        this.monitoredInterval = monitoredInterval;
        this.owner = owner;
    }
}
