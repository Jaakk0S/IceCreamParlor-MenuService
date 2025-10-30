package com.catsoft.demo.icecreamparlor.repository;

import com.catsoft.demo.icecreamparlor.jpa.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findById(int id);
}
