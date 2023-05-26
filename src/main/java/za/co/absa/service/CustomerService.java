package za.co.absa.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import za.co.absa.exception.NotEnoughProductQuantityException;
import za.co.absa.exception.ProductNotFoundException;
import za.co.absa.exception.UserAlreadyExistException;
import za.co.absa.exception.UserNotFoundException;
import za.co.absa.model.domain.Customer;
import za.co.absa.model.domain.Product;
import za.co.absa.model.domain.Purchase;
import za.co.absa.model.dto.customer.*;
import za.co.absa.model.dto.purchase.PurchaseRequestDto;
import za.co.absa.repository.CustomerRepository;
import za.co.absa.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public CustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto) {
        Optional<Customer> foundCustomer = customerRepository.findByName(requestDto.getName());

        if (foundCustomer.isPresent()) {
            throw new UserAlreadyExistException(requestDto.getName());
        }

        Customer customer = modelMapper.map(requestDto, Customer.class);
        Customer savedEntity = customerRepository.save(customer);

        return modelMapper.map(savedEntity, CustomerResponseDto.class);
    }

    public CustomerResponseDto findById(Long id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        return foundCustomer
                .map(customer -> modelMapper.map(customer, CustomerResponseDto.class))
                .orElseThrow(() -> new UserNotFoundException(id));
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

    public CustomerResponseDto purchase(Long customerId, PurchaseRequestDto requestDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        Product product = productRepository.findById(requestDto.productId())
                .orElseThrow(() -> new ProductNotFoundException(requestDto.productId()));

        validateAvailableQuantityOfProduct(product.getUnitsAvailable(), requestDto.unitsToBuy());

        product.setUnitsAvailable(product.getUnitsAvailable() - requestDto.unitsToBuy());
        Purchase purchase = createPurchase(product, requestDto.unitsToBuy());
        customer.addPurchase(purchase);

        Customer savedEntity = customerRepository.save(customer);
        return modelMapper.map(savedEntity, CustomerResponseDto.class);
    }

    public CustomerStatsDto findCustomerStats(CustomerSearchQueryDto queryDto) {
        CustomerStatsProjection projection =
                customerRepository.findNumberOfPurchasesAndAverageExpenditureWithinTimeFrame(
                        queryDto.getCustomerId(),
                        queryDto.getFromDate(),
                        queryDto.getToDate());

        return new CustomerStatsDto(projection.getNumberOfPurchases(), projection.getAverageBought(),
                queryDto.getFromDate(), queryDto.getToDate(), null);
    }

    private Purchase createPurchase(Product product, Long unitsToBuy) {
        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setUnitsBought(unitsToBuy);
        return purchase;
    }

    private void validateAvailableQuantityOfProduct(Long unitsAvailable, Long unitsToBuy) {
        if (unitsAvailable < unitsToBuy) {
            throw new NotEnoughProductQuantityException(unitsAvailable, unitsToBuy);
        }
    }
}
