package org.cubingtr.cubingtrapi.wca.repository;

import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WcaCompetitionRepository extends JpaRepository<WcaCompetitionEntity, Long> {

    WcaCompetitionEntity findById(String id);

    List<WcaCompetitionEntity> findAllByCountryidAndStartDateAfter(String countryId, LocalDate startDate);

    List<WcaCompetitionEntity> findAllByCountryidAndStartDateBefore(String countryId, LocalDate startDate);

}
