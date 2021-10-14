package nandor.ledenyi.bankapp.service;

import nandor.ledenyi.bankapp.entity.Customer;
import nandor.ledenyi.bankapp.exception.EntityNotFoundException;
import nandor.ledenyi.bankapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer, Long id) {
        customer.setId(id);
        return save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
