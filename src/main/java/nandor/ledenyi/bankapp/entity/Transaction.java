package nandor.ledenyi.bankapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime transactionTime;
    private double amount;
    private String description;
    @ManyToOne
    private Account account;

    public Transaction(LocalDateTime transactionTime, double amount, String description, Account account) {
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.description = description;
        this.account = account;
    }
}
