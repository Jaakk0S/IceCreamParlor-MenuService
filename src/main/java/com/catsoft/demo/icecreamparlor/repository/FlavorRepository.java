package com.catsoft.demo.icecreamparlor.repository;

import com.catsoft.demo.icecreamparlor.jpa.Flavor;
import org.springframework.data.repository.CrudRepository;

public interface FlavorRepository extends CrudRepository<Flavor, Integer> {
    Flavor findById(int id);
}
