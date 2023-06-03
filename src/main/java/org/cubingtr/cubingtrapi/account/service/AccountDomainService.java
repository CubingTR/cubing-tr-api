package org.cubingtr.cubingtrapi.account.service;

import lombok.RequiredArgsConstructor;
import org.cubingtr.cubingtrapi.account.entity.AccountEntity;
import org.cubingtr.cubingtrapi.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDomainService {
    private final AccountRepository accountRepository;

    public AccountEntity save(AccountEntity accountEntity) {
        accountEntity = accountRepository.save(accountEntity);
        return accountEntity;
    }

    public AccountEntity findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    public AccountEntity findAccountByWcaPk(Long wcaPk) {
        return accountRepository.findAccountByWcaPk(wcaPk);
    }

}
