package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import com.catsoft.demo.icecreamparlor.jpa.Cone;
import com.catsoft.demo.icecreamparlor.jpa.Flavor;
import com.catsoft.demo.icecreamparlor.jpa.Product;
import com.catsoft.demo.icecreamparlor.repository.FlavorRepository;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/menu/v1")
public class FlavorController {


    /*
        NOTE: Because flavor API is a simple CRUD API, there is no service layer.
     */


    @Autowired
    private FlavorRepository flavorRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/flavor")
    List<FlavorDTO> getAllFlavors() {
        return StreamSupport.stream(this.flavorRepository.findAll().spliterator(), false).map(Flavor::toDTO).toList();
    }

    @PostMapping("/flavor")
    FlavorDTO addFlavor(@RequestBody FlavorDTO flavor) {
        Flavor entity = Flavor.builder().name(flavor.name()).build();
        entity = this.flavorRepository.save(entity);
        return entity.toDTO();
    }

    @GetMapping("/flavor/{id}")
    FlavorDTO getFlavor(@PathVariable Integer id) {
        return this.flavorRepository.findById(id).map(Flavor::toDTO).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatusCode.valueOf(404));
        });
    }

    @DeleteMapping("/flavor/{id}")
    void deleteFlavor(@PathVariable Integer id) {
        this.flavorRepository.findById(id).ifPresentOrElse(
                f -> {
                    if (f.getProducts().isEmpty())
                        this.flavorRepository.deleteById(id);
                    else
                        throw new ResponseStatusException(HttpStatusCode.valueOf(409));
                },
                () -> { throw new ResponseStatusException(HttpStatusCode.valueOf(204)); }
        );
    }
}
