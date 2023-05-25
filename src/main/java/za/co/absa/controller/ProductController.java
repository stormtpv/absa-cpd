package za.co.absa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.absa.model.domain.Product;
import za.co.absa.model.dto.customer.CreateCustomerRequestDto;
import za.co.absa.model.dto.customer.CustomerResponseDto;
import za.co.absa.model.dto.customer.UpdateCustomerRequestDto;
import za.co.absa.model.dto.product.CreateProductRequestDto;
import za.co.absa.model.dto.product.ProductResponseDto;
import za.co.absa.model.dto.product.UpdateProductRequestDto;
import za.co.absa.service.ProductService;
import za.co.absa.util.ProductModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductModelAssembler assembler;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateProductRequestDto requestDto) {
        ProductResponseDto product = productService.createProduct(requestDto);
        EntityModel<ProductResponseDto> entityModel = assembler.toModel(product);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<ProductResponseDto> getById(@PathVariable("id") Long id) {
        ProductResponseDto productResponse = productService.findById(id);
        return assembler.toModel(productResponse);
    }

    @GetMapping
    public CollectionModel<EntityModel<ProductResponseDto>> findAll() {
        List<EntityModel<ProductResponseDto>> products = productService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).findAll()).withSelfRel());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                                     @RequestBody UpdateProductRequestDto requestDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(id, requestDto);
        EntityModel<ProductResponseDto> entityModel = assembler.toModel(productResponseDto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
