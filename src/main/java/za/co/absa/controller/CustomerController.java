package za.co.absa.controller;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import za.co.absa.model.dto.customer.*;
import za.co.absa.model.dto.purchase.PurchaseRequestDto;
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
    @Transactional
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
    @Transactional
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody UpdateCustomerRequestDto requestDto) {
        CustomerResponseDto customerResponseDto = customerService.updateCustomer(id, requestDto);
        EntityModel<CustomerResponseDto> entityModel = assembler.toModel(customerResponseDto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/purchase")
    @Transactional
    public ResponseEntity<?> makePurchase(@PathVariable("id") Long customerId,
                                          @RequestBody PurchaseRequestDto requestDto) {
        CustomerResponseDto customerResponseDto = customerService.purchase(customerId, requestDto);
        EntityModel<CustomerResponseDto> entityModel = assembler.toModel(customerResponseDto);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/purchase/stats")
    public CustomerStatsDto getCustomerStats(CustomerSearchQueryDto queryDto) {
        return customerService.findCustomerStats(queryDto);
    }

}
