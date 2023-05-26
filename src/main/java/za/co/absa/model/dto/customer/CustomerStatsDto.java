package za.co.absa.model.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatsDto {

    private Long numberOfPurchases;

    private Double averageBought;

    @JsonIgnore
    private LocalDate fromDate;

    @JsonIgnore
    private LocalDate toDate;

    private String purchaseDateRange;

    public String getPurchaseDateRange() {
        return "Date range [" + fromDate + " - " + toDate + "]";
    }
}
