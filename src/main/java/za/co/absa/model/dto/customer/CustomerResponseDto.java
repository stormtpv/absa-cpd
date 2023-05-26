package za.co.absa.model.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import za.co.absa.model.dto.purchase.PurchaseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private String address;

    private List<PurchaseDto> purchases = new ArrayList<>();
}
