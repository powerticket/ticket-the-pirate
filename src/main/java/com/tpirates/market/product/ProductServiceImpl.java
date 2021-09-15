package com.tpirates.market.product;

import com.tpirates.market.common.ErrorInfo;
import com.tpirates.market.common.exception.NotFoundException;
import com.tpirates.market.product.dto.*;
import com.tpirates.market.product.entity.Product;
import com.tpirates.market.product.entity.ProductDelivery;
import com.tpirates.market.product.entity.ProductOption;
import com.tpirates.market.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
        throw new NotFoundException(ErrorInfo.PRODUCT_NOT_FOUND.getErrorCode(), ErrorInfo.PRODUCT_NOT_FOUND.getErrorMessage());
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
        throw new NotFoundException(ErrorInfo.PRODUCT_NOT_FOUND.getErrorCode(), ErrorInfo.PRODUCT_NOT_FOUND.getErrorMessage());
    }

    @Override
    public List<ProductDeliveryDateDto> readDeliveryDate(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Date createdAt = product.getCreatedAt();
            ProductDelivery delivery = product.getDelivery();
            String deliveryClosing = delivery.getClosing();
            String deliveryType = delivery.getType();
            return calculateDeliveryDate(createdAt, deliveryClosing, deliveryType);
        }
        throw new NotFoundException(ErrorInfo.PRODUCT_NOT_FOUND.getErrorCode(), ErrorInfo.PRODUCT_NOT_FOUND.getErrorMessage());
    }

    private List<ProductDeliveryDateDto> calculateDeliveryDate(Date date, String deliveryClosing, String deliveryType) {
        List<ProductDeliveryDateDto> productDeliveryDateDtos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Calendar deliveryClosingCalendar = Calendar.getInstance();
        String[] deliveryClosingSplit = deliveryClosing.split(":");
        int hour = Integer.parseInt(deliveryClosingSplit[0]);
        int minute = Integer.parseInt(deliveryClosingSplit[1]);
        deliveryClosingCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, 0);

        if (calendar.compareTo(deliveryClosingCalendar) > 0) calendar.add(Calendar.DATE, 1);
        if (!deliveryType.equals("fast")) calendar.add(Calendar.DATE, 1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) calendar.add(Calendar.DATE, 2);
        else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, 1);

        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("M월 d일 EEEE", Locale.KOREAN);
        while (i < 5) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                productDeliveryDateDtos.add(new ProductDeliveryDateDto(dateFormat.format(calendar.getTime())));
                i++;
            }
            calendar.add(Calendar.DATE, 1);
        }

        return productDeliveryDateDtos;
    }


    @Override
    public void delete(Long id) {

        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new NotFoundException(ErrorInfo.PRODUCT_NOT_FOUND.getErrorCode(), ErrorInfo.PRODUCT_NOT_FOUND.getErrorMessage());
        });
    }
}
