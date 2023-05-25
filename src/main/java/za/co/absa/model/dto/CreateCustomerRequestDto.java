package za.co.absa.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class CreateCustomerRequestDto {

    private String name;

    private LocalDate birthDate;

    private String address;
}
