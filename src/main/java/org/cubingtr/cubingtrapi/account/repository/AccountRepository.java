package org.cubingtr.cubingtrapi.account.repository;

import org.cubingtr.cubingtrapi.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findAccountByEmail(String userEmail);

    AccountEntity findAccountByWcaPk(Long wcaPk);

}
