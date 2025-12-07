package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.dto.ProductDTO;
import com.catsoft.demo.icecreamparlor.jpa.Cone;
import com.catsoft.demo.icecreamparlor.jpa.Flavor;
import com.catsoft.demo.icecreamparlor.jpa.Product;
import com.catsoft.demo.icecreamparlor.jpa.Topping;
import com.catsoft.demo.icecreamparlor.repository.ConeRepository;
import com.catsoft.demo.icecreamparlor.repository.FlavorRepository;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import com.catsoft.demo.icecreamparlor.repository.ToppingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/menu/v1")
public class ProductController {


    /*
        NOTE: Because product API is a simple CRUD API, there is no service layer.
     */


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationContext context;


    @GetMapping("/menu-entry")
    List<ProductDTO> getAllProducts() {
        return StreamSupport.stream(this.productRepository.findAll().spliterator(), false).map(p -> p.toDTO(false)).toList();
    }

    @PostMapping("/menu-entry")
    ProductDTO addProduct(@Valid @RequestBody ProductDTO product) {
        Product entity = Product.builder()
                .name(product.name())
                .cone(Cone.builder().id(product.cone().id()).build())
                .flavor(Flavor.builder().id(product.flavor().id()).build())
                .toppings(product.toppings().stream().map(
                        t -> Topping.builder().id(t.id())
                                .build()
                ).toList())
                .build();
        entity = this.productRepository.save(entity);
        return entity.toDTO(false);
    }

    @GetMapping("/menu-entry/{id}")
    ProductDTO getProduct(@PathVariable Integer id) {
        return this.productRepository.findById(id).map(p -> p.toDTO(false)).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatusCode.valueOf(404));
        });
    }

    @DeleteMapping("/menu-entry/{id}")
    void deleteProduct(@PathVariable Integer id) {
        this.productRepository.deleteById(id);
        throw new ResponseStatusException(HttpStatusCode.valueOf(204));
    }

}
