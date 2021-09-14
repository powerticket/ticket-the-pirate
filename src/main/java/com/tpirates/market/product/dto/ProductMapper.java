package com.tpirates.market.product.dto;

import com.tpirates.market.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productPostDtoToProduct(ProductPostDto productPostDto);
}
