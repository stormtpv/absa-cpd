package za.co.absa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import za.co.absa.model.domain.Product;
import za.co.absa.model.domain.Purchase;
import za.co.absa.model.dto.customer.CustomerStatsProjection;

import java.time.LocalDate;
import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
@ActiveProfiles("test")
@Sql(value = "classpath:/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void givenNameWhenFindByNameThenReturnSuccess() {
        // given
        String name = "Paul";

        // when
        customerRepository.findByName(name)
                .ifPresent(customer -> {
                    // then
                    Assertions.assertEquals("Miami", customer.getAddress());
                    Assertions.assertEquals(1L, customer.getId());
                    Assertions.assertEquals(LocalDate.of(2023, 5, 12), customer.getRegistrationDate());
                    Assertions.assertEquals(LocalDate.of(1987, 1, 12), customer.getBirthDate());
                });
    }

    @Test
    @Transactional
    void givenCustomerWhenMakePurchaseThenSuccess() {
        // given
        List<Product> products = productRepository.findAll();

        // when
        customerRepository.findByName("Paul")
                .ifPresent(customer -> {
                    products.forEach(product -> {
                        Purchase purchase = new Purchase();
                        purchase.setCustomer(customer);
                        purchase.setProduct(product);
                        purchase.setDate(LocalDate.now());
                        purchase.setUnitsBought(1L);
                        customer.addPurchase(purchase);
                    });
                    customerRepository.save(customer);
                });

        customerRepository.findByName("Paul")
                .ifPresent(customer -> {
                    Assertions.assertEquals(3L, customer.getPurchases().size());
                });
    }

    @Test
    @Transactional
    void givenCustomerWhenGetAverageExpenditureWithinTimeFrameThenReturnSuccess() {
        // given
        List<Product> products = productRepository.findAll();
        List<Double> totalSum = new ArrayList<>();
        Map<Long, Long> productPriceToQuantity = new HashMap<>();

        // when
        customerRepository.findByName("Paul")
                .ifPresent(customer -> {
                    products.forEach(product -> {
                        Purchase purchase = new Purchase();
                        purchase.setCustomer(customer);
                        purchase.setProduct(product);
                        purchase.setDate(LocalDate.now());
                        long unitsToBuy = new Random().nextLong(10);
                        totalSum.add(product.getPrice());
                        purchase.setUnitsBought(unitsToBuy);
                        customer.addPurchase(purchase);
                    });
                    customerRepository.save(customer);
                });

        customerRepository.findByName("Paul")
                .ifPresent(customer -> {
                    CustomerStatsProjection stats = customerRepository.findNumberOfPurchasesAndAverageExpenditureWithinTimeFrame(customer.getId(),
                            LocalDate.now().minusDays(1L),
                            LocalDate.now().plusDays(1L));

                    // then
                    Assertions.assertEquals(3L, stats.getNumberOfPurchases());
                    Assertions.assertEquals(totalSum.stream()
                            .mapToDouble(Double::doubleValue)
                            .summaryStatistics().getAverage(), stats.getAverageBought());
                });
    }
}