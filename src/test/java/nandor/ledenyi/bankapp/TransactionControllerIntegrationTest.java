package nandor.ledenyi.bankapp;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.operation.OperationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionControllerIntegrationTest {

    @LocalServerPort
    private Integer port;
    private final String BASE_URL = "http://localhost:";
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testDepositToAccount_shouldReturnNewBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OperationRequest> httpEntity = new HttpEntity<>(new OperationRequest(0L, 1L, 5000), headers);
        ResponseEntity<String> postResponse = testRestTemplate.postForEntity(BASE_URL + port + "/transaction/deposit", httpEntity, String.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Account account = testRestTemplate.getForObject(BASE_URL + port + "/account/1", Account.class);
        assertEquals(15000, account.getBalance());
    }

    @Test
    public void testWithdrawFromAccount_shouldReturnNewBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OperationRequest> httpEntity = new HttpEntity<>(new OperationRequest(2L, 0L, 5000), headers);
        ResponseEntity<String> postResponse = testRestTemplate.postForEntity(BASE_URL + port + "/transaction/withdraw", httpEntity, String.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Account account = testRestTemplate.getForObject(BASE_URL + port + "/account/2", Account.class);
        assertEquals(7000, account.getBalance());
    }

    @Test
    public void testTransferBetweenAccounts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OperationRequest> httpEntity = new HttpEntity<>(new OperationRequest(1L, 2L, 5000), headers);
        ResponseEntity<String> postResponse = testRestTemplate.postForEntity(BASE_URL + port + "/transaction/transfer", httpEntity, String.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Account account1 = testRestTemplate.getForObject(BASE_URL + port + "/account/1", Account.class);
        Account account2 = testRestTemplate.getForObject(BASE_URL + port + "/account/2", Account.class);
        assertEquals(5000, account1.getBalance());
        assertEquals(17000, account2.getBalance());
    }
}
