package za.co.absa.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import za.co.absa.model.dto.customer.*;
import za.co.absa.model.dto.purchase.PurchaseDto;
import za.co.absa.model.dto.purchase.PurchaseRequestDto;
import za.co.absa.service.CustomerService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static za.co.absa.controller.CustomerControllerTest.CreateCustomerFixtures.*;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    private static final String CUSTOMERS_URI = "/customers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void givenCreateCustomerRequestWhenCreateThenReturnCustomerResponse() throws Exception {
        // given
        LocalDate localDate = getLocalDate();
        CreateCustomerRequestDto customerRequestDto = createCustomerRequestDto("Paul", "Miami", localDate);
        CustomerResponseDto expectedResponse = customerResponseDto("Paul", "Miami", localDate, 1L);

        // given mocked
        when(customerService.createCustomer(customerRequestDto))
                .thenReturn(expectedResponse);

        // when
        this.mockMvc.perform(post(CUSTOMERS_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(customerRequestDto)))
                // then
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void givenCustomerIdWhenGetByIdThenReturnSuccess() throws Exception {
        // given
        Long customerId = 1L;
        LocalDate localDate = getLocalDate();
        CustomerResponseDto expectedResponse = customerResponseDto("Paul", "Miami", localDate, 1L);

        // given mocked
        when(customerService.findById(customerId))
                .thenReturn(expectedResponse);

        // when
        this.mockMvc.perform(get(CUSTOMERS_URI + "/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void givenRequestWhenFindlAllThenReturnListOfCustomers() throws Exception {
        // given
        LocalDate localDate = getLocalDate();
        List<CustomerResponseDto> expectedResponse = Arrays.asList(
                customerResponseDto("Paul", "Miami", localDate, 1L),
                customerResponseDto("Paul", "Miami", localDate, 2L)
        );

        // given mocked
        when(customerService.findAll()).thenReturn(expectedResponse);

        // when
        MvcResult mvcResult = this.mockMvc.perform(get(CUSTOMERS_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<CustomerResponseDto> actualResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // then
        Assertions.assertAll(() -> {
            Assertions.assertEquals(2L, actualResponse.size());
            Assertions.assertEquals(expectedResponse, actualResponse);
        });
    }

    @Test
    void givenUpdateRequestWhenUpdateCustomerThenUpdateSuccess() throws Exception {
        // given
        Long customerId = 1L;
        LocalDate localDate = getLocalDate();
        UpdateCustomerRequestDto updateRequest = createUpdateCustomerRequestDto(localDate, "San Francisco", "James");
        CustomerResponseDto expectedResponse = customerResponseDto("James", "San Francisco", localDate, 1L);

        // given mocked
        when(customerService.updateCustomer(customerId, updateRequest))
                .thenReturn(expectedResponse);

        // when
        this.mockMvc.perform(put(CUSTOMERS_URI + "/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(updateRequest)))
                // then
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void deleteById() throws Exception {
        // given
        Long customerId = 1L;

        // given mocked
        doNothing().when(customerService)
                .deleteById(customerId);

        // when
        this.mockMvc.perform(delete(CUSTOMERS_URI + "/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void givenPurchaseRequestWhenCustomerMakesPurachseThenSuccess() throws Exception {
        // given
        Long customerId = 1L;
        Long productId = 1L;
        Long unitsToBuy = 5L;
        Long purchaseId = 1L;
        LocalDate purchaseDate = getLocalDate();
        Long unitsToBought = 5L;
        PurchaseRequestDto requestDto = createPurchaseRequestDto(productId, unitsToBuy);
        CustomerResponseDto expectedResponse = customerResponseDto("Paul", "Miami", getLocalDate(), customerId);
        expectedResponse.setPurchases(List.of(createPurchaseDto(purchaseId, purchaseDate, unitsToBought)));

        // given mocked
        when(customerService.purchase(customerId, requestDto))
                .thenReturn(expectedResponse);

        // when
        this.mockMvc.perform(post(CUSTOMERS_URI + "/{id}/purchase", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                // then
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void givenRequestWhenRequestThenReturnCustomerStats() throws Exception {
        // given
        Long customerId = 1L;
        LocalDate fromDate = getLocalDate();
        LocalDate toDate = getLocalDate().plusDays(1L);
        CustomerSearchQueryDto searchQueryDto = createSearchQueryDto(customerId, fromDate, toDate);
        Double averageBought = 13.0;
        Long numberOfPurchases = 12L;
        CustomerStatsDto expectedResponse = createCustomerStatsDto(fromDate, toDate, averageBought, numberOfPurchases);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("customerId", Collections.singletonList(customerId.toString()));
        queryParams.put("fromDate", Collections.singletonList(fromDate.toString()));
        queryParams.put("toDate", Collections.singletonList(toDate.toString()));

        // given mocked
        when(customerService.findCustomerStats(searchQueryDto))
                .thenReturn(expectedResponse);

        // when
        this.mockMvc.perform(get(CUSTOMERS_URI + "/purchase/stats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(queryParams))
                // then
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    static class CreateCustomerFixtures {

        public static LocalDate getLocalDate() {
            return LocalDate.now();
        }

        public static CreateCustomerRequestDto createCustomerRequestDto(String name,
                                                                        String address,
                                                                        LocalDate birthDate) {
            CreateCustomerRequestDto requestDto = new CreateCustomerRequestDto();
            requestDto.setName(name);
            requestDto.setAddress(address);
            requestDto.setBirthDate(birthDate);
            return requestDto;
        }

        public static CustomerResponseDto customerResponseDto(String name,
                                                              String address,
                                                              LocalDate birthDate,
                                                              Long id) {
            CustomerResponseDto responseDto = new CustomerResponseDto();
            responseDto.setName(name);
            responseDto.setAddress(address);
            responseDto.setBirthDate(birthDate);
            responseDto.setId(id);
            return responseDto;
        }

        public static UpdateCustomerRequestDto createUpdateCustomerRequestDto(LocalDate birthDate,
                                                                              String address,
                                                                              String name) {
            UpdateCustomerRequestDto requestDto = new UpdateCustomerRequestDto();
            requestDto.setName(name);
            requestDto.setAddress(address);
            requestDto.setBirthDate(birthDate);
            return requestDto;
        }

        public static PurchaseRequestDto createPurchaseRequestDto(Long productId, Long unitsToBuy) {
            PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto();
            purchaseRequestDto.setProductId(productId);
            purchaseRequestDto.setUnitsToBuy(unitsToBuy);
            return purchaseRequestDto;
        }

        public static PurchaseDto createPurchaseDto(Long purchaseId, LocalDate purchaseDate, Long unitsToBought) {
            PurchaseDto purchaseDto = new PurchaseDto();
            purchaseDto.setId(purchaseId);
            purchaseDto.setDate(purchaseDate);
            purchaseDto.setUnitsBought(unitsToBought);
            return purchaseDto;
        }

        public static CustomerSearchQueryDto createSearchQueryDto(Long customerId, LocalDate fromDate, LocalDate toDate) {
            CustomerSearchQueryDto searchQueryDto = new CustomerSearchQueryDto();
            searchQueryDto.setCustomerId(customerId);
            searchQueryDto.setFromDate(fromDate);
            searchQueryDto.setToDate(toDate);
            return searchQueryDto;
        }

        public static CustomerStatsDto createCustomerStatsDto(LocalDate fromDate, LocalDate toDate,
                                                              Double averageBought, Long numberOfPurchases) {
            CustomerStatsDto statsDto = new CustomerStatsDto();
            statsDto.setFromDate(fromDate);
            statsDto.setToDate(toDate);
            statsDto.setAverageBought(averageBought);
            statsDto.setNumberOfPurchases(numberOfPurchases);
            return statsDto;
        }
    }
}