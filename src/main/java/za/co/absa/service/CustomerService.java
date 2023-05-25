package za.co.absa.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import za.co.absa.exception.UserNotFoundException;
import za.co.absa.model.domain.Customer;
import za.co.absa.model.dto.customer.CreateCustomerRequestDto;
import za.co.absa.model.dto.customer.CustomerResponseDto;
import za.co.absa.model.dto.customer.UpdateCustomerRequestDto;
import za.co.absa.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto) {
        Optional<Customer> foundCustomer = customerRepository.findByName(requestDto.getName());

        if (foundCustomer.isPresent()) {
            throw new UserNotFoundException(requestDto.getName());
        }

        Customer customer = modelMapper.map(requestDto, Customer.class);
        Customer savedEntity = customerRepository.save(customer);

        return modelMapper.map(savedEntity, CustomerResponseDto.class);
    }

    public CustomerResponseDto findById(Long id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        return foundCustomer
                .map(customer -> modelMapper.map(customer, CustomerResponseDto.class))
                .orElse(null);
    }

    public CustomerResponseDto updateCustomer(Long id, UpdateCustomerRequestDto requestDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        modelMapper.map(requestDto, customer);

        Customer updatedEntity = customerRepository.save(customer);

        return modelMapper.map(updatedEntity, CustomerResponseDto.class);
    }

    public void deleteById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

       customerRepository.delete(customer);
    }

    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseDto.class))
                .collect(Collectors.toList());
    }
}
