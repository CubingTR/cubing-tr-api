package org.cubingtr.cubingtrapi.wca.repository;

import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEntity;
import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcaCompetitionEventRepository extends JpaRepository<WcaCompetitionEventEntity, Long> {

    List<WcaCompetitionEventEntity> findAllByCompetitionId(String competitionId);

    WcaCompetitionEventEntity findByCompetitionIdAndEventId(String competitionId, String eventId);

}
