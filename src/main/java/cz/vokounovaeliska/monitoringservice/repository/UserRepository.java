package cz.vokounovaeliska.monitoringservice.repository;

import cz.vokounovaeliska.monitoringservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
