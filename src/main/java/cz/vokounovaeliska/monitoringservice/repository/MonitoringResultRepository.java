package cz.vokounovaeliska.monitoringservice.repository;

import cz.vokounovaeliska.monitoringservice.entity.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {
}
