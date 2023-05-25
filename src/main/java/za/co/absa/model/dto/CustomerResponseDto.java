package za.co.absa.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerResponseDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private String address;
}
