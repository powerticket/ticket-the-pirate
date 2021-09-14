package com.tpirates.market.product;

import com.tpirates.market.product.dto.ProductDetailDto;
import com.tpirates.market.product.dto.ProductDto;
import com.tpirates.market.product.dto.ProductPostDto;
import com.tpirates.market.product.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(ProductPostDto productPostDto);

    List<ProductDto> readAll();

    ProductDetailDto readOne(Long id);

    Product update(ProductPostDto productPostDto);

    void delete(Long id);

}
