package nandor.ledenyi.bankapp.repository;

import nandor.ledenyi.bankapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findCustomersByAccounts(Long Id);
    List<Customer> findCustomersByLastName(String lastName);
}
