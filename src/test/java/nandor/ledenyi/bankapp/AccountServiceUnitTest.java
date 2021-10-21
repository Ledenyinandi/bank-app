package nandor.ledenyi.bankapp;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.entity.AccountStatus;
import nandor.ledenyi.bankapp.entity.AccountType;
import nandor.ledenyi.bankapp.entity.Customer;
import nandor.ledenyi.bankapp.repository.AccountRepository;
import nandor.ledenyi.bankapp.repository.CustomerRepository;
import nandor.ledenyi.bankapp.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceUnitTest {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private AccountService accountService;
    private Account account;
    private Customer customer;

    @BeforeEach
    void initUseCase() {
        accountRepository = mock(AccountRepository.class);
        customerRepository = mock(CustomerRepository.class);
        accountService = new AccountService(accountRepository, customerRepository);
        customer = new Customer(1L, "Szabó", "Zoltán", "Hungary", "Szeged", "Béke 11", "6000",
                "+36709876123", "szabozoli@gmail.com");
        account = new Account(1L, "12345678-00000000-00000000", AccountType.SAVINGS, AccountStatus.ACTIVE, 5000,
                5000, LocalDateTime.now(), customer);
    }

    @Test
    public void testSave_shouldReturnNewAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Account savedAccount = accountRepository.save(account);
        assertEquals("12345678-00000000-00000000", savedAccount.getAccountNumber());
    }

    @Test
    public void testFindAll_shouldReturnListOfAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        when(accountRepository.findAll()).thenReturn(accounts);
        List<Account> fetchedAccounts = new ArrayList<>();
        accountService.findAll().forEach(account1 -> fetchedAccounts.add(account1));
        assertEquals(5000, fetchedAccounts.get(0).getBalance());
    }

    @Test
    public void testFindById_shouldReturnAccountWithGivenId() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Account foundAccount = accountService.findById(1L);
        assertEquals(5000, foundAccount.getBalance());
    }
}
