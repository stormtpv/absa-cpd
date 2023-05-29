
package za.co.absa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import za.co.absa.model.dto.customer.*;
import za.co.absa.model.dto.purchase.PurchaseRequestDto;
import za.co.absa.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @Transactional
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CreateCustomerRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(requestDto));
    }

    @GetMapping("/{id}")
    public CustomerResponseDto getById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping
    public List<CustomerResponseDto> findAll() {
        return customerService.findAll();
    }

    @PutMapping("/{id}")
    @Transactional
    public CustomerResponseDto update(@PathVariable("id") Long id,
                                      @RequestBody UpdateCustomerRequestDto requestDto) {
        return customerService.updateCustomer(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/purchase")
    @Transactional
    public CustomerResponseDto makePurchase(@PathVariable("id") Long customerId,
                                            @RequestBody PurchaseRequestDto requestDto) {
        return customerService.purchase(customerId, requestDto);
    }

    @GetMapping("/purchase/stats")
    public CustomerStatsDto getCustomerStats(CustomerSearchQueryDto queryDto) {
        return customerService.findCustomerStats(queryDto);
    }

}
