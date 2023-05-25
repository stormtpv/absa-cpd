package za.co.absa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.absa.model.dto.CreateCustomerRequestDto;
import za.co.absa.model.dto.CustomerResponseDto;
import za.co.absa.model.dto.UpdateCustomerRequestDto;
import za.co.absa.service.CustomerService;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CreateCustomerRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable("id") Long id,
                                                      @RequestBody UpdateCustomerRequestDto requestDto) {
        return ResponseEntity.ok(customerService.updateCustomer(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
