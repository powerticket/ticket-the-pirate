package com.tpirates.market.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpirates.market.common.exception.NotFoundException;
import com.tpirates.market.product.dto.ProductDeliveryDateDto;
import com.tpirates.market.product.dto.ProductDetailDto;
import com.tpirates.market.product.dto.ProductDto;
import com.tpirates.market.product.dto.ProductPostDto;
import com.tpirates.market.product.entity.Product;
import com.tpirates.market.product.repository.MemoryProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceImplTest {

    private final MemoryProductRepository memoryProductRepository = new MemoryProductRepository();
    private final ProductService productService = new ProductServiceImpl(memoryProductRepository);

    private final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        memoryProductRepository.clearStore();
    }

    @Test
    @DisplayName("점포 추가 API")
    void productCreate() throws JsonProcessingException {
        ProductPostDto product = getSalmonProductPostDto();

        Product createdProduct = productService.create(product);

        List<ProductDto> productDtoList = productService.readAll();

        assertThat(productDtoList.size()).isEqualTo(1);
        assertThat(createdProduct.getName()).isEqualTo(product.getName());
        assertThat(createdProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(createdProduct.getDelivery()).isEqualTo(product.getDelivery());
        assertThat(createdProduct.getOptions().toString()).isEqualTo(product.getOptions().toString());
    }


    @Test
    @DisplayName("상품 목록 조회 API")
    void readProductList() throws JsonProcessingException, InterruptedException {
        ProductPostDto product1 = getSalmonProductPostDto();
        ProductPostDto product2 = getAbaloneProductPostDto();

        Date createdAt1 = productService.create(product1).getCreatedAt();
        TimeUnit.MILLISECONDS.sleep(1);
        Date createdAt2 = productService.create(product2).getCreatedAt();

        assertThat(createdAt1.compareTo(createdAt2)).isEqualTo(-1);

        List<ProductDto> productDto = productService.readAll();

        String answer = "[\n" +
                "    {\n" +
                "        \"name\": \"완도전복\",\n" +
                "        \"description\": \"산지직송 완도 전복 1kg (7미~60미)\",\n" +
                "        \"price\": \"20,000 ~ \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"노르웨이산 연어\",\n" +
                "        \"description\": \"노르웨이산 연어 300g, 500g, 반마리 필렛\",\n" +
                "        \"price\": \"10,000 ~ \"\n" +
                "    }\n" +
                "]";

        List<ProductDto> parsedProductDto = mapper.readValue(answer, new TypeReference<>() {
        });

        assertThat(productDto.toString()).isEqualTo(parsedProductDto.toString());
    }

    @Test
    @DisplayName("상품 상세조회 API")
    void readProductDetail() throws JsonProcessingException {

        ProductPostDto product = getSalmonProductPostDto();

        Product createdProduct = productService.create(product);

        ProductDetailDto productDetailDto = productService.readOne(createdProduct.getId());

        String answer = "{\n" +
                "\"name\": \"노르웨이산 연어\",\n" +
                "\"description\": \"노르웨이산 연어 300g, 500g, 반마리 필렛\",\n" +
                "\"delivery\": \"fast\",\n" +
                "\"options\": [\n" +
                "{\n" +
                "\"name\": \"생연어 몸통살 300g\",\n" +
                "\"price\": 10000\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"생연어 몸통살 500g\",\n" +
                "\"price\": 17000\n" +
                "}\n" +
                "]\n" +
                "}";
        ProductDetailDto answerProductDetailDto = mapper.readValue(answer, ProductDetailDto.class);

        assertThat(productDetailDto.toString()).isEqualTo(answerProductDetailDto.toString());
    }

    @Test
    @DisplayName("수령일 선택 목록 API")
    void readDeliveryDate() throws JsonProcessingException {

        ProductPostDto product = getSalmonProductPostDto();

        Product createdProduct = productService.create(product);

        List<ProductDeliveryDateDto> productDeliveryDateDtoList = productService.readDeliveryDate(createdProduct.getId());

        assertThat(productDeliveryDateDtoList.size()).isEqualTo(5);

    }

    @Test
    @DisplayName("점포 삭제 API")
    void deleteProduct() throws JsonProcessingException {

        ProductPostDto product = getSalmonProductPostDto();

        Product createdProduct = productService.create(product);
        assertThat(productService.readOne(createdProduct.getId())).isNotNull();

        productService.delete(createdProduct.getId());
        Assertions.assertThrows(NotFoundException.class, () ->
                productService.readOne(createdProduct.getId())
        );

    }

    private ProductPostDto getSalmonProductPostDto() throws JsonProcessingException {
        return mapper.readValue("{\n" +
                "    \"name\": \"노르웨이산 연어\",\n" +
                "    \"description\": \"노르웨이산 연어 300g, 500g, 반마리 필렛\",\n" +
                "    \"delivery\": {\n" +
                "        \"type\": \"fast\",\n" +
                "        \"closing\": \"12:00\"\n" +
                "    },\n" +
                "    \"options\": [\n" +
                "        {\n" +
                "            \"name\": \"생연어 몸통살 300g\",\n" +
                "            \"price\": 10000,\n" +
                "            \"stock\": 99\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"생연어 몸통살 500g\",\n" +
                "            \"price\": 17000,\n" +
                "            \"stock\": 99\n" +
                "        }\n" +
                "    ]\n" +
                "}", ProductPostDto.class);
    }

    private ProductPostDto getAbaloneProductPostDto() throws JsonProcessingException {
        return mapper.readValue("{\n" +
                "    \"name\": \"완도전복\",\n" +
                "    \"description\": \"산지직송 완도 전복 1kg (7미~60미)\",\n" +
                "    \"delivery\": {\n" +
                "        \"type\": \"regular\",\n" +
                "        \"closing\": \"18:00\"\n" +
                "    },\n" +
                "    \"options\": [\n" +
                "        {\n" +
                "            \"name\": \"대 7~8미\",\n" +
                "            \"price\": 50000,\n" +
                "            \"stock\": 99\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"중 14~15미\",\n" +
                "            \"price\": 34000,\n" +
                "            \"stock\": 99\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"소 50~60미\",\n" +
                "            \"price\": 20000,\n" +
                "            \"stock\": 99\n" +
                "        }\n" +
                "    ]\n" +
                "}", ProductPostDto.class);
    }
}
