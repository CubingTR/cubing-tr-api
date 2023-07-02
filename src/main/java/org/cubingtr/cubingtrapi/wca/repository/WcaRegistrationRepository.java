package org.cubingtr.cubingtrapi.wca.repository;

import org.cubingtr.cubingtrapi.wca.entity.WcaEventEntity;
import org.cubingtr.cubingtrapi.wca.entity.WcaRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcaRegistrationRepository extends JpaRepository<WcaRegistrationEntity, Long> {

    WcaRegistrationEntity findById(String id);

    WcaRegistrationEntity findByCompetitionIdAndUserId(String competitionId, Long userId);

    List<WcaRegistrationEntity> findAllByCompetitionId(String competitionId);

}
