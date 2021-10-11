package nandor.ledenyi.bankapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AccountType accountType;
    private double balance;
    private double beginBalance;
    private LocalDateTime beginBalanceTime;
    @ManyToMany
    private List<Customer> customers;
    @OneToMany
    private List<Transaction> transaction;
}
