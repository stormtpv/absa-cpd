package za.co.absa.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.absa.model.domain.Customer;
import za.co.absa.model.domain.Purchase;
import za.co.absa.model.dto.customer.CustomerResponseDto;
import za.co.absa.model.dto.purchase.PurchaseDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        configureCustomerToCustomerDto(modelMapper);
        return modelMapper;
    }

    private void configureCustomerToCustomerDto(ModelMapper modelMapper) {
        Converter<Set<Purchase>, List<PurchaseDto>> toPurchaseDto = ctx -> {
            Set<Purchase> source = ctx.getSource();
            return source.stream()
                    .map(s -> {
                        return new PurchaseDto(s.getId(), s.getUnitsBought(), s.getDate());
                    })
                    .collect(Collectors.toList());
        };

        modelMapper.typeMap(Customer.class, CustomerResponseDto.class)
                .addMappings(mapping -> mapping
                        .using(toPurchaseDto)
                        .map(Customer::getPurchases, CustomerResponseDto::setPurchases)
                );
    }
}
