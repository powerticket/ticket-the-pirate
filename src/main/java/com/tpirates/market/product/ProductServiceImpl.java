package com.tpirates.market.product;

import com.tpirates.market.product.dto.*;
import com.tpirates.market.product.entity.Product;
import com.tpirates.market.product.entity.ProductOption;
import com.tpirates.market.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Override
    public Product create(ProductPostDto productPostDto) {
        return productRepository.save(ProductMapper.INSTANCE.productPostDtoToProduct(productPostDto));
    }

    @Override
    public List<ProductDto> readAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDto = new ArrayList<>();
        productList.stream().sorted(Comparator.comparing(Product::getCreatedAt).reversed()).forEach(product -> {
            Optional<ProductOption> minPrice = product.getOptions().stream().min(Comparator.comparingLong(ProductOption::getPrice));
            String price = "";
            if (minPrice.isPresent()) {
                price = NumberFormat.getInstance().format(minPrice.get().getPrice()) + " ~ ";
            }
            productDto.add(
                    ProductDto.builder()
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(price)
                            .build()
            );
        });
        return productDto;
    }

    @Override
    public ProductDetailDto readOne(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            List<ProductDetailOptionDto> productDetailOptionDtos = product.getOptions().stream().map(productOption -> ProductDetailOptionDto.builder()
                    .name(productOption.getName())
                    .price(productOption.getPrice())
                    .build()
            ).collect(Collectors.toList());

            return ProductDetailDto.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .delivery(product.getDelivery().getType())
                    .options(productDetailOptionDtos)
                    .build();
        }
        return null;
    }

    @Override
    public List<ProductDeliveryDateDto> readDeliveryDate(Long productId) {
        return null;
    }

    @Override
    public Product update(ProductPostDto productPostDto) {
        return null;
    }


    @Override
    public void delete(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }
}
