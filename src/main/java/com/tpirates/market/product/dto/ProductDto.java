package com.tpirates.market.product.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private String name;
    private String description;
    private String price;
}
