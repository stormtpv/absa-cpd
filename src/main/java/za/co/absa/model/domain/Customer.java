package za.co.absa.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "customers")
@Table(name = "customers",
        uniqueConstraints =
        @UniqueConstraint(name = "customer_name_birthDate_uc", columnNames = {
                "name", "birthDate"
        }))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_gen")
    @SequenceGenerator(name = "customers_gen", sequenceName = "customers_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private String address;

    @CreatedDate
    private LocalDate registrationDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private Set<Purchase> purchases = new LinkedHashSet<>();

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.setCustomer(this);
    }

    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
        purchase.setCustomer(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
