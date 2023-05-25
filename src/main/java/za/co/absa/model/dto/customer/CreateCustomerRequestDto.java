package za.co.absa.model.dto.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerRequestDto {

    private String name;

    private LocalDate birthDate;

    private String address;
}
