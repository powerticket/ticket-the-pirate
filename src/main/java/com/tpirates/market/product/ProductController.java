package com.tpirates.market.product;

import com.tpirates.market.product.dto.ProductDetailDto;
import com.tpirates.market.product.dto.ProductDto;
import com.tpirates.market.product.dto.ProductPostDto;
import com.tpirates.market.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProductList() {

        List<ProductDto> productDtos = productService.readAll();
        System.out.println("productDtos = " + productDtos);

        return productDtos;
    }

    @GetMapping("/{productId}")
    public ProductDetailDto getProductDetail(@PathVariable("productId") Long productId) {
        return productService.readOne(productId);
    }

    @PostMapping
    public Product registerProduct(@RequestBody ProductPostDto productPostDto) {
        return productService.create(productPostDto);
    }
}
