package com.catsoft.demo.icecreamparlor.repository;

import com.catsoft.demo.icecreamparlor.jpa.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findById(int id);

    @Query(value = "SELECT * FROM PRODUCT_TOPPINGS WHERE PRODUCT_ID=:productId", nativeQuery = true)
    Object[] findRowsInProductToppings(@Param("productId") int productId);
}
