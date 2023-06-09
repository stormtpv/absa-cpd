package za.co.absa.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "products",
        uniqueConstraints =
        @UniqueConstraint(name = "product_name_uc", columnNames = {"name"}))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_gen")
    @SequenceGenerator(name = "products_gen", sequenceName = "products_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    @NaturalId
    private String name;

    private String description;

    private Double price;

    private Long unitsAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
