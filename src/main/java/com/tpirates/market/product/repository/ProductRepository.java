package com.tpirates.market.product.repository;

import com.tpirates.market.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long productId);

    List<Product> findAll();

    void delete(Product product);

}
