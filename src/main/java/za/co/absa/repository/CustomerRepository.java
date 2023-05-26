package za.co.absa.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.absa.model.domain.Customer;
import za.co.absa.model.dto.customer.CustomerStatsProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByName(String name);

    @Query("select count (p) as numberOfPurchases, IFNULL(AVG(p.product.price),0) as averageBought from customers c " +
            "left join purchases p on c.id = p.customer.id " +
            "where c.id = :customerId and p.date between :fromDate and :toDate")
    CustomerStatsProjection findNumberOfPurchasesAndAverageExpenditureWithinTimeFrame(@Param("customerId") Long customerId,
                                                                                      @Param("fromDate") LocalDate fromDate,
                                                                                      @Param("toDate") LocalDate toDate);
}
