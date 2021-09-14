package com.tpirates.market.product;

import com.tpirates.market.product.entity.Product;
import com.tpirates.market.product.repository.MemoryProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryProductRepositoryTest {

    MemoryProductRepository memoryProductRepository = new MemoryProductRepository();

    @AfterEach
    void tearDown() {
        memoryProductRepository.clearStore();
    }

    @Test
    void saveAndFindById() {
        Product product = new Product();
        Product save = memoryProductRepository.save(product);
        Optional<Product> optionalProduct = memoryProductRepository.findById(save.getId());
        System.out.println("save = " + save);
        System.out.println("optionalProduct = " + optionalProduct);
        assertThat(optionalProduct.isPresent()).isEqualTo(true);
    }

    @Test
    void findAll() {
        Product product1 = new Product();
        Product product2 = new Product();

        memoryProductRepository.save(product1);
        memoryProductRepository.save(product2);

        List<Product> all = memoryProductRepository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }
}