package com.example.springboot;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ProductService {

    private HashMap<Integer, Product> personMap = new HashMap<Integer, Product>();

    public void addProduct(Product product) {
        personMap.put(product.getId(), product);
    }

    public Product getProduct(Integer id) {
        return personMap.get(id);
    }
}
