package za.co.absa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.absa.model.dto.product.CreateProductRequestDto;
import za.co.absa.model.dto.product.ProductResponseDto;
import za.co.absa.model.dto.product.UpdateProductRequestDto;
import za.co.absa.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody CreateProductRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(requestDto));
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable("id") Long id,
                                     @RequestBody UpdateProductRequestDto requestDto) {
        return productService.updateProduct(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
