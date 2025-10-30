package com.catsoft.demo.icecreamparlor.repository;

import com.catsoft.demo.icecreamparlor.jpa.Cone;
import org.springframework.data.repository.CrudRepository;

public interface ConeRepository extends CrudRepository<Cone, Integer> {
    Cone findById(int id);
}
