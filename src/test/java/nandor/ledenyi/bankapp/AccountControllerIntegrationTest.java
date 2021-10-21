package nandor.ledenyi.bankapp;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.entity.AccountStatus;
import nandor.ledenyi.bankapp.entity.AccountType;
import nandor.ledenyi.bankapp.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerIntegrationTest {

    @LocalServerPort
    private Integer port;
    private final String BASE_URL = "http://localhost:";
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testFindAll_shouldReturnListOfAccounts() {
        ResponseEntity<Account[]> responseEntity = testRestTemplate.getForEntity(BASE_URL + port + "/account", Account[].class);
        List<Account> actual = Arrays.asList(responseEntity.getBody());
        assertEquals(2, actual.size());
        assertEquals("12345678-98765432-00000000", actual.get(0).getAccountNumber());
    }

    @Test
    public void testFindById_shouldReturnAccountWithGivenId() {
        Account account = testRestTemplate.getForObject(BASE_URL + port + "/account/2", Account.class);
        assertEquals("12345678-98765432-23459876", account.getAccountNumber());
    }

    @Test
    public void testSave_shouldAddNewAccount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Customer customer = testRestTemplate.getForObject(BASE_URL + port + "/customer/1", Customer.class);
        HttpEntity<Account> httpEntity = new HttpEntity<>(new Account(0L, "12345678-00000000-00000000",
                AccountType.SAVINGS, AccountStatus.ACTIVE, 5000, 5000, LocalDateTime.now(), customer), headers);
        ResponseEntity<String> postResponse = testRestTemplate.postForEntity(BASE_URL + port + "/account", httpEntity, String.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Account account = testRestTemplate.getForObject(BASE_URL + port + "/account/3", Account.class);
        assertEquals(5000, account.getBalance());
    }

    @Test
    public void testUpdate_shouldUpdateAccountByGivenId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Account account = testRestTemplate.getForObject(BASE_URL + port + "/account/2", Account.class);
        Customer customer = testRestTemplate.getForObject(BASE_URL + port + "/customer/1", Customer.class);
        HttpEntity<Account> httpEntity = new HttpEntity<>(new Account(2L, "00000000-00000000-00000000", AccountType.RETIREMENT,
                AccountStatus.ACTIVE, 1000, 1000, account.getBeginTime(), customer));
        testRestTemplate.put(BASE_URL + port + "/account/2", httpEntity, String.class);
        Account actualAccount = testRestTemplate.getForObject(BASE_URL + port + "/account/2", Account.class);
        assertEquals("00000000-00000000-00000000", actualAccount.getAccountNumber());
        assertEquals(AccountType.RETIREMENT, actualAccount.getAccountType());
    }

    @Test
    public void testDelete_shouldDeleteAccountByGivenId() {
        testRestTemplate.delete(BASE_URL + port + "/account/2");
        ResponseEntity<Account[]> responseEntity = testRestTemplate.getForEntity(BASE_URL + port + "/account", Account[].class);
        List<Account> actual = Arrays.asList(responseEntity.getBody());
        assertEquals(1, actual.size());
    }

    @Test
    public void testGetBalanceByAccountNumber_shouldReturnBalance() {
        double balance = testRestTemplate.getForObject(BASE_URL + port + "/account/balance?accountNumber=12345678-98765432-00000000", Double.class);
        assertEquals(10000, balance);
    }

    @Test
    public void testGetBalanceById_shouldReturnBalance() {
        double balance = testRestTemplate.getForObject(BASE_URL + port + "/account/balance/1", Double.class);
        assertEquals(10000, balance);
    }
}
