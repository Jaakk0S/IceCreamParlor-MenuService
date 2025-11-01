package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.jpa.Cone;
import com.catsoft.demo.icecreamparlor.jpa.Product;
import com.catsoft.demo.icecreamparlor.repository.ConeRepository;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/admin/v1")
public class ConeController {


    /*
        NOTE: Because cone API is a simple CRUD API, there is no service layer.
     */


    @Autowired
    private ConeRepository coneRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/cone")
    List<ConeDTO> getAllCones() {
        return StreamSupport.stream(this.coneRepository.findAll().spliterator(), false).map(Cone::toDTO).toList();
    }

    @PostMapping("/cone")
    ConeDTO addCone(@RequestBody ConeDTO cone) {
        Cone entity = Cone.builder().name(cone.name()).build();
        entity = this.coneRepository.save(entity);
        return entity.toDTO();
    }

    @GetMapping("/cone/{id}")
    ConeDTO getCone(@PathVariable Integer id) {
        return this.coneRepository.findById(id).map(Cone::toDTO).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatusCode.valueOf(404));
        });
    }

    @DeleteMapping("/cone/{id}")
    void deleteCone(@PathVariable Integer id) {
        this.coneRepository.findById(id).ifPresentOrElse(
                t -> {
                    List<Product> productsWithCone = StreamSupport.stream(this.productRepository.findAll().spliterator(),
                            false).filter(p -> p.getCone().getId() == id).toList();
                    if (productsWithCone.isEmpty())
                        this.coneRepository.deleteById(id);
                    else
                        throw new ResponseStatusException(HttpStatusCode.valueOf(409));
                },
                () -> { throw new ResponseStatusException(HttpStatusCode.valueOf(204)); }
        );
    }
}
