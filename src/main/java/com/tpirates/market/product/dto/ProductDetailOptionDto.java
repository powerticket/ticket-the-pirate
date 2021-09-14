package com.tpirates.market.product.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ProductDetailOptionDto {
    private String name;
    private Long price;
}
