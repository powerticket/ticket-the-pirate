package com.tpirates.market.product.dto;

import com.tpirates.market.product.entity.Product;
import com.tpirates.market.product.entity.ProductDelivery;
import com.tpirates.market.product.entity.ProductOption;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    @Test
    void mappingTest() {
        ProductPostDto productPostDto = new ProductPostDto("name", "desc", new ProductDelivery(), List.of(new ProductOption()));
        Product product = ProductMapper.INSTANCE.productPostDtoToProduct(productPostDto);
        System.out.println("productCreateDto = " + productPostDto);
        System.out.println("product = " + product);
        assertThat(product.getName()).isEqualTo(productPostDto.getName());
        assertThat(product.getDescription()).isEqualTo(productPostDto.getDescription());
        assertThat(product.getDelivery()).isEqualTo(productPostDto.getDelivery());
        assertThat(product.getOptions()).isEqualTo(productPostDto.getOptions());
    }
}