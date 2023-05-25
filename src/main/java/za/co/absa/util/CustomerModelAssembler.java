package za.co.absa.util;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import za.co.absa.controller.CustomerController;
import za.co.absa.controller.ProductController;
import za.co.absa.model.dto.customer.CustomerResponseDto;
import za.co.absa.model.dto.product.ProductResponseDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<CustomerResponseDto, EntityModel<CustomerResponseDto>> {
    @Override
    public EntityModel<CustomerResponseDto> toModel(CustomerResponseDto product) {
        return EntityModel.of(product,
                linkTo(methodOn(CustomerController.class).getById(product.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).findAll()).withRel("customers"));
    }
}
