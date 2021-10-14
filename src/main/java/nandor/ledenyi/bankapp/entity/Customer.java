package nandor.ledenyi.bankapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Cannot be empty!")
    private String lastName;
    @NotBlank(message = "Cannot be empty!")
    private String firstName;
    @NotBlank(message = "Cannot be empty!")
    private String country;
    @NotBlank(message = "Cannot be empty!")
    private String city;
    @NotBlank(message = "Cannot be empty!")
    private String address;
    @NotBlank(message = "Cannot be empty!")
    private String zip;
    @Pattern(regexp="(^$|[0-9]{11})")
    private String phoneNumber;
    @Email
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
