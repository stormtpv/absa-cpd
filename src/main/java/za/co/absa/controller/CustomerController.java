package za.co.absa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.absa.model.dto.customer.CreateCustomerRequestDto;
import za.co.absa.model.dto.customer.CustomerResponseDto;
import za.co.absa.model.dto.customer.UpdateCustomerRequestDto;
import za.co.absa.model.dto.product.ProductResponseDto;
import za.co.absa.service.CustomerService;
import za.co.absa.util.CustomerModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerModelAssembler assembler;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCustomerRequestDto requestDto) {
        CustomerResponseDto customer = customerService.createCustomer(requestDto);
        EntityModel<CustomerResponseDto> entityModel = assembler.toModel(customer);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<CustomerResponseDto> getById(@PathVariable("id") Long id) {
        CustomerResponseDto customerResponse = customerService.findById(id);
        return assembler.toModel(customerResponse);
    }

    @GetMapping
    public CollectionModel<EntityModel<CustomerResponseDto>> findAll() {
        List<EntityModel<CustomerResponseDto>> products = customerService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(CustomerController.class).findAll()).withSelfRel());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                                      @RequestBody UpdateCustomerRequestDto requestDto) {
        CustomerResponseDto customerResponseDto = customerService.updateCustomer(id, requestDto);
        EntityModel<CustomerResponseDto> entityModel = assembler.toModel(customerResponseDto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
