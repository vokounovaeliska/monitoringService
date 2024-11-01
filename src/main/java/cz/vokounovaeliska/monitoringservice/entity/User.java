package cz.vokounovaeliska.monitoringservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = true, unique = true)
    private String accessToken;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.accessToken = UUID.randomUUID().toString();
    }
}
