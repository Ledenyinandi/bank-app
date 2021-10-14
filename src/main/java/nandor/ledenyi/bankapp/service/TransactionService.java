package nandor.ledenyi.bankapp.service;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.entity.Transaction;
import nandor.ledenyi.bankapp.exception.EntityNotFoundException;
import nandor.ledenyi.bankapp.exception.IdenticalIdException;
import nandor.ledenyi.bankapp.exception.InsufficientCreditException;
import nandor.ledenyi.bankapp.operation.OperationRequest;
import nandor.ledenyi.bankapp.repository.AccountRepository;
import nandor.ledenyi.bankapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public void depositToAccount(OperationRequest operationRequest) {
        Account currentAccount = accountRepository.findById(operationRequest.getToAccountId()).orElseThrow(EntityNotFoundException::new);
        Transaction transaction = new Transaction(LocalDateTime.now(), operationRequest.getAmount(), "deposit", currentAccount);
        transactionRepository.save(transaction);
        currentAccount.setBalance(currentAccount.getBalance() + operationRequest.getAmount());
        accountRepository.save(currentAccount);
    }

    public void withdrawFromAccount(OperationRequest operationRequest) {
        Account currentAccount = accountRepository.findById(operationRequest.getFromAccountId()).orElseThrow(EntityNotFoundException::new);
        if (currentAccount.getBalance() < operationRequest.getAmount()) {
            throw new InsufficientCreditException();
        }
        Transaction transaction = new Transaction(LocalDateTime.now(), operationRequest.getAmount(), "withdraw", currentAccount);
        transactionRepository.save(transaction);
        currentAccount.setBalance(currentAccount.getBalance() - operationRequest.getAmount());
        accountRepository.save(currentAccount);
    }

    public void transferBetweenAccounts(OperationRequest operationRequest) {
        if (operationRequest.getFromAccountId().equals(operationRequest.getToAccountId())) {
            throw new IdenticalIdException();
        }
        withdrawFromAccount(operationRequest);
        depositToAccount(operationRequest);
    }
}
