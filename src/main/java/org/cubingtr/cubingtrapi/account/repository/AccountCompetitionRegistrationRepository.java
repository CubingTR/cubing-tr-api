package org.cubingtr.cubingtrapi.account.repository;

import org.cubingtr.cubingtrapi.account.entity.AccountCompetitionRegistrationEntity;
import org.cubingtr.cubingtrapi.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountCompetitionRegistrationRepository extends JpaRepository<AccountCompetitionRegistrationEntity, Long> {

    AccountCompetitionRegistrationEntity findByUserIdAndCompetitionIdAndEventId(Long userId, String competitionId, String eventId);

    List<AccountCompetitionRegistrationEntity> findAllByUserIdAndCompetitionId(Long userId, String competitionId);

}
