package com.tpirates.market.product.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ProductDetailDto {
    private String name;
    private String description;
    private String delivery;
    private List<ProductDetailOptionDto> options;

}
