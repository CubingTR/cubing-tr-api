package org.cubingtr.cubingtrapi.account.service;

import lombok.RequiredArgsConstructor;
import org.cubingtr.cubingtrapi.account.entity.AccountCompetitionRegistrationEntity;
import org.cubingtr.cubingtrapi.account.model.AccountCompetitionRegistrationRequest;
import org.cubingtr.cubingtrapi.account.repository.AccountCompetitionRegistrationRepository;
import org.cubingtr.cubingtrapi.auth.model.AuthenticatedUser;
import org.cubingtr.cubingtrapi.common.service.SecurityContextHelperService;
import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEventEntity;
import org.cubingtr.cubingtrapi.wca.repository.WcaCompetitionEventRepository;
import org.cubingtr.cubingtrapi.wca.repository.WcaCompetitionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountRestControllerService {

    private final SecurityContextHelperService securityContextHelperService;
    private final AccountCompetitionRegistrationRepository accountCompetitionRegistrationRepository;
    private final WcaCompetitionEventRepository wcaCompetitionEventRepository;

    public List<AccountCompetitionRegistrationEntity> register(AccountCompetitionRegistrationRequest accountCompetitionRegistrationRequest) {
        AuthenticatedUser authenticatedUser = securityContextHelperService.getAuthenticatedUser();

        Long userId = authenticatedUser.getWcaPk();
        String competitionId = accountCompetitionRegistrationRequest.getCompetitionId();

        for (String eventId : accountCompetitionRegistrationRequest.getEventIdList()) {

            WcaCompetitionEventEntity wcaCompetitionEventEntity = wcaCompetitionEventRepository.findByCompetitionIdAndEventId(competitionId, eventId);
            if (wcaCompetitionEventEntity == null) {
                throw new IllegalArgumentException(String.format("Competition [%s] does not have event [%s]", competitionId, eventId));
            }

            AccountCompetitionRegistrationEntity accountCompetitionRegistrationEntity = accountCompetitionRegistrationRepository.findByUserIdAndCompetitionIdAndEventId(userId, competitionId, eventId);

            if (accountCompetitionRegistrationEntity == null) {
                accountCompetitionRegistrationEntity = new AccountCompetitionRegistrationEntity();
                accountCompetitionRegistrationEntity.setUserId(userId);
                accountCompetitionRegistrationEntity.setCompetitionId(competitionId);
                accountCompetitionRegistrationEntity.setEventId(eventId);
                accountCompetitionRegistrationEntity.setRegistrationDate(LocalDate.now());
                accountCompetitionRegistrationRepository.save(accountCompetitionRegistrationEntity);
            }

        }

        return accountCompetitionRegistrationRepository.findAllByUserIdAndCompetitionId(userId, competitionId);
    }

}
