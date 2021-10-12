package nandor.ledenyi.bankapp.service;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.entity.AccountType;
import nandor.ledenyi.bankapp.entity.Customer;
import nandor.ledenyi.bankapp.exception.EntityNotFoundException;
import nandor.ledenyi.bankapp.exception.IllegalAccountTypeException;
import nandor.ledenyi.bankapp.repository.AccountRepository;
import nandor.ledenyi.bankapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (!account.getAccountType().equals(AccountType.CHECKING) ||
                !account.getAccountType().equals(AccountType.SAVINGS) ||
                !account.getAccountType().equals(AccountType.CERTIFICATE_OF_DEPOSIT) ||
                !account.getAccountType().equals(AccountType.MONEY_MARKET) ||
                !account.getAccountType().equals(AccountType.RETIREMENT)) {
            throw new IllegalAccountTypeException();
        }
        if (account.getCustomers() == null || account.getCustomers() != customerRepository.findCustomersByAccounts(account.getId())) {
            throw new EntityNotFoundException();
        }
        return accountRepository.save(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public void addCustomerToAccount(Long customerId, Long accountId) {
        Customer customerToAdd = customerRepository.findById(customerId).orElseThrow(EntityNotFoundException::new);
        Account account1 = findById(accountId);
        account1.addCustomer(customerToAdd);
    }

    public List<Account> findAccountsByCustomer(Long customerId) {
        return accountRepository.findAccountsByCustomers(customerId);
    }

}
