package com.tpirates.market.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpirates.market.product.dto.ProductDetailDto;
import com.tpirates.market.product.dto.ProductDto;
import com.tpirates.market.product.dto.ProductPostDto;
import com.tpirates.market.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("상품 추가")
    void productCreate() throws JsonProcessingException {
        ProductPostDto product = mapper.readValue("{\n" +
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

        Product createdProduct = productService.create(product);

        List<ProductDto> productDtos = productService.readAll();

        assertThat(productDtos.size()).isEqualTo(1);
        assertThat(createdProduct.getName()).isEqualTo(product.getName());
        assertThat(createdProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(createdProduct.getDelivery()).isEqualTo(product.getDelivery());
        assertThat(createdProduct.getOptions().toString()).isEqualTo(product.getOptions().toString());
    }

    @Test
    @DisplayName("상품 목록 조회")
    void productList() throws JsonProcessingException {
        ProductPostDto product1 = mapper.readValue("{\n" +
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

        ProductPostDto product2 = mapper.readValue("{\n" +
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

        productService.create(product1);
        productService.create(product2);

        List<ProductDto> productDto = productService.readAll();

        System.out.println("productDto = " + productDto);

//        String answer = "[\n" +
//                "    {\n" +
//                "        \"name\": \"완도전복\",\n" +
//                "        \"description\": \"산지직송 완도 전복 1kg (7미~60미)\",\n" +
//                "        \"price\": \"50,000 ~ \"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"name\": \"노르웨이산 연어\",\n" +
//                "        \"description\": \"노르웨이산 연어 300g, 500g, 반마리 필렛\",\n" +
//                "        \"price\": \"10,000 ~ \"\n" +
//                "    }\n" +
//                "]";
//
//        List<ProductDto> parsedProductDto = mapper.readValue(answer, new TypeReference<List<ProductDto>>() {
//        });
//        System.out.println("parsedProductDto = " + parsedProductDto);

    }
}