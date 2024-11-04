package cz.vokounovaeliska.monitoringservice.repository;

import cz.vokounovaeliska.monitoringservice.entity.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {
    List<MonitoringResult> findByMonitoredEndpointIdOrderByDateOfCheckDesc(Long monitoredEndpointId);

    List<MonitoringResult> findAllByOrderByDateOfCheckDesc();
}
