package nandor.ledenyi.bankapp.repository;

import nandor.ledenyi.bankapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
