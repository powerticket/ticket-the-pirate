package com.tpirates.market.product;

import com.tpirates.market.product.dto.ProductDeliveryDateDto;
import com.tpirates.market.product.dto.ProductDetailDto;
import com.tpirates.market.product.dto.ProductDto;
import com.tpirates.market.product.dto.ProductPostDto;
import com.tpirates.market.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @DeleteMapping("/{productId}")
    public Map<String, String> deleteProduct(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("status", "success");
        return map;
    }

    @GetMapping("/delivery/{productId}")
    public List<ProductDeliveryDateDto> getProductDeliverySelect(@PathVariable("productId") Long productId) {
        return productService.readDeliveryDate(productId);
    }

    @PostMapping
    public Product registerProduct(@RequestBody ProductPostDto productPostDto) {
        return productService.create(productPostDto);
    }
}
