package com.catsoft.demo.icecreamparlor.repository;

import com.catsoft.demo.icecreamparlor.jpa.Topping;
import org.springframework.data.repository.CrudRepository;

public interface ToppingRepository extends CrudRepository<Topping, Integer> {
    Topping findById(int id);
}
