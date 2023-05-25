package za.co.absa.model.dto.product;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateProductRequestDto {

    private String name;

    private String description;

    private Double price;

    private Long unitsAvailable;
}
