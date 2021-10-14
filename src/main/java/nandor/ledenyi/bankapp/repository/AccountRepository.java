package nandor.ledenyi.bankapp.repository;

import nandor.ledenyi.bankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select t.balance from Account t where t.accountNumber = :accountNumber")
    double getBalanceByAccountNumber(String accountNumber);
}
