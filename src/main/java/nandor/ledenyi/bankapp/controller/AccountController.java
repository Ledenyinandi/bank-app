package nandor.ledenyi.bankapp.controller;

import nandor.ledenyi.bankapp.entity.Account;
import nandor.ledenyi.bankapp.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable("id") Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    public Account save(@Valid @RequestBody Account account) {
        return accountService.save(account);
    }

    @PutMapping("/{id}")
    public Account update(@Valid @RequestBody Account account, @PathVariable("id") Long id) {
        return accountService.update(account, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        accountService.deleteById(id);
    }
}
