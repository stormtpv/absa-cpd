package za.co.absa.model.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {

    private Long id;

    private Long unitsBought;

    private LocalDate date;
}
