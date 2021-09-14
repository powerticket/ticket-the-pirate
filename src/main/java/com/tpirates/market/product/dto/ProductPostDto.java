package com.tpirates.market.product.dto;

import com.tpirates.market.product.entity.ProductDelivery;
import com.tpirates.market.product.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPostDto {

    private String name;
    private String description;

    private ProductDelivery delivery;

    private List<ProductOption> options;
}
