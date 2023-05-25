package za.co.absa.util;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import za.co.absa.controller.ProductController;
import za.co.absa.model.domain.Product;
import za.co.absa.model.dto.product.ProductResponseDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductResponseDto, EntityModel<ProductResponseDto>> {
    @Override
    public EntityModel<ProductResponseDto> toModel(ProductResponseDto product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).findAll()).withRel("products"));
    }
}
