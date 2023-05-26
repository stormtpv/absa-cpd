package za.co.absa.model.dto.customer;

public interface CustomerStatsProjection {

    Long getNumberOfPurchases();

    Double getAverageBought();
}
