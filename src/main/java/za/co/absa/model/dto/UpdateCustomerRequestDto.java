package za.co.absa.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCustomerRequestDto {

    private String name;

    private LocalDate birthDate;

    private String address;
}
