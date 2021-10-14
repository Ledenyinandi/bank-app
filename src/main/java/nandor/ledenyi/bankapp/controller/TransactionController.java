package nandor.ledenyi.bankapp.controller;

import nandor.ledenyi.bankapp.operation.OperationRequest;
import nandor.ledenyi.bankapp.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositToAccount(@RequestBody OperationRequest operationRequest) {
        transactionService.depositToAccount(operationRequest);
        return ResponseEntity.ok().body("Amount deposited");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawFromAccount(@RequestBody OperationRequest operationRequest) {
        transactionService.withdrawFromAccount(operationRequest);
        return ResponseEntity.ok().body("Amount withdrew");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferBetweenAccounts(@RequestBody OperationRequest operationRequest) {
        transactionService.transferBetweenAccounts(operationRequest);
        return ResponseEntity.ok().body("Amount transferred");
    }
}
