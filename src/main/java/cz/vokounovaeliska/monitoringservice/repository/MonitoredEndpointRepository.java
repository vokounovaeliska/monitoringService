package cz.vokounovaeliska.monitoringservice.repository;

import cz.vokounovaeliska.monitoringservice.entity.MonitoredEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {
}
