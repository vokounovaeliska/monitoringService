package cz.vokounovaeliska.monitoringservice.repository;

import cz.vokounovaeliska.monitoringservice.entity.MonitoredEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {
    List<MonitoredEndpoint> findAllByOwnerId(Long ownerId);
}
