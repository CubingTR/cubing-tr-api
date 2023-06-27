package org.cubingtr.cubingtrapi.wca.repository;

import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEntity;
import org.cubingtr.cubingtrapi.wca.entity.WcaEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcaEventRepository extends JpaRepository<WcaEventEntity, Long> {

    WcaEventEntity findById(String id);

    List<WcaEventEntity> findAll();

}
