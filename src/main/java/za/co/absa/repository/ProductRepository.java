package za.co.absa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.absa.model.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
