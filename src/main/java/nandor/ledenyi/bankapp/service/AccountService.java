package nandor.ledenyi.bankapp.service;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.exception.EntityNotFoundException;
import nandor.ledenyi.bankapp.repository.AccountRepository;
import nandor.ledenyi.bankapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Account save(Account account) {
        if (account.getCustomer().getId() == null || !Objects.equals(customerRepository.findById(account.getCustomer().getId()).orElse(null), account.getCustomer())) {
            throw new EntityNotFoundException();
        }
        return accountRepository.save(account);
    }

    public Account update(Account account, Long id) {
        account.setId(id);
        return save(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
}
