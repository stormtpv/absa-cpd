package za.co.absa.model.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSearchQueryDto {

    private Long customerId;

    private LocalDate fromDate;

    private LocalDate toDate;
}
