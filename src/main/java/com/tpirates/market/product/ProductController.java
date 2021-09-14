package com.tpirates.market.product;

import com.tpirates.market.common.dto.ResponseDto;
import com.tpirates.market.product.dto.ProductDeliveryDateDto;
import com.tpirates.market.product.dto.ProductDetailDto;
import com.tpirates.market.product.dto.ProductDto;
import com.tpirates.market.product.dto.ProductPostDto;
import com.tpirates.market.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @GetMapping
    public ResponseDto<List<ProductDto>> getProductList() {
        return ResponseDto.<List<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .data(productService.readAll())
                .build();
    }

    @GetMapping("/{productId}")
    public ResponseDto<ProductDetailDto> getProductDetail(@PathVariable("productId") Long productId) {
        return ResponseDto.<ProductDetailDto>builder()
                .status(HttpStatus.OK.value())
                .data(productService.readOne(productId))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseDto<Long> deleteProduct(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return ResponseDto.<Long>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .data(productId)
                .build();
    }

    @GetMapping("/delivery/{productId}")
    public ResponseDto<List<ProductDeliveryDateDto>> getProductDeliverySelect(@PathVariable("productId") Long productId) {
        return ResponseDto.<List<ProductDeliveryDateDto>>builder()
                .status(HttpStatus.OK.value())
                .data(productService.readDeliveryDate(productId))
                .build();
    }

    @PostMapping
    public ResponseDto<Product> registerProduct(@RequestBody ProductPostDto productPostDto) {
        return ResponseDto.<Product>builder()
                .status(HttpStatus.CREATED.value())
                .data(productService.create(productPostDto))
                .build();
    }
}
