package com.tpirates.market.product.repository;

import com.tpirates.market.product.entity.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MemoryProductRepository implements ProductRepository {

    private static Map<Long, Product> store = new ConcurrentHashMap<>();
    private static long seq = 0L;

    @Override
    public Product save(Product product) {
        Date date = new Date(System.currentTimeMillis());
        product.setId(++seq);
        product.setCreatedAt(date);
        product.setUpdatedAt(date);
        store.put(seq, product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(store.get(productId));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Product product) {
        store.remove(product.getId());
    }

    public void clearStore() {
        store.clear();
    }
}
