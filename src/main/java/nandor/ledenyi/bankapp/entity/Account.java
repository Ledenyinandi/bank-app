package nandor.ledenyi.bankapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Cannot be empty!")
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private double balance;
    private double beginBalance;
    private LocalDateTime beginTime;
    @ManyToOne
    private Customer customer;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Transaction> transaction;

    public Account(Long id, String accountNumber, AccountType accountType, AccountStatus accountStatus, double balance,
                   double beginBalance, LocalDateTime beginTime, Customer customer) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = balance;
        this.beginBalance = beginBalance;
        this.beginTime = beginTime;
        this.customer = customer;
    }
}
