package za.co.absa.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import za.co.absa.exception.ProductNotFoundException;
import za.co.absa.exception.UserNotFoundException;
import za.co.absa.model.domain.Customer;
import za.co.absa.model.domain.Product;
import za.co.absa.model.dto.customer.CreateCustomerRequestDto;
import za.co.absa.model.dto.customer.CustomerResponseDto;
import za.co.absa.model.dto.customer.UpdateCustomerRequestDto;
import za.co.absa.model.dto.product.CreateProductRequestDto;
import za.co.absa.model.dto.product.ProductResponseDto;
import za.co.absa.model.dto.product.UpdateProductRequestDto;
import za.co.absa.repository.CustomerRepository;
import za.co.absa.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {
        Optional<Product> foundProduct = productRepository.findByName(requestDto.getName());

        if (foundProduct.isPresent()) {
            throw new ProductNotFoundException(requestDto.getName());
        }

        Product product = modelMapper.map(requestDto, Product.class);
        Product savedEntity = productRepository.save(product);

        return modelMapper.map(savedEntity, ProductResponseDto.class);
    }

    public ProductResponseDto findById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .orElse(null);
    }

    public ProductResponseDto updateProduct(Long id, UpdateProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        modelMapper.map(requestDto, product);

        Product updatedEntity = productRepository.save(product);

        return modelMapper.map(updatedEntity, ProductResponseDto.class);
    }

    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }
}
