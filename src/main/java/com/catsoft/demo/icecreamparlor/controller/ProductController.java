package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.dto.ProductDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import com.catsoft.demo.icecreamparlor.jpa.Cone;
import com.catsoft.demo.icecreamparlor.jpa.Flavor;
import com.catsoft.demo.icecreamparlor.jpa.Product;
import com.catsoft.demo.icecreamparlor.jpa.Topping;
import com.catsoft.demo.icecreamparlor.repository.ConeRepository;
import com.catsoft.demo.icecreamparlor.repository.FlavorRepository;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import com.catsoft.demo.icecreamparlor.repository.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/admin/v1")
public class ProductController {


    /*
        NOTE: Because product API is a simple CRUD API, there is no service layer.
     */


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConeRepository coneRepository;

    @Autowired
    private FlavorRepository flavorRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private ApplicationContext context;


    @GetMapping("/menu-entry")
    List<ProductDTO> getAllProducts() {
        return StreamSupport.stream(this.productRepository.findAll().spliterator(), false).map(Product::toDTO).toList();
    }

    @PostMapping("/menu-entry")
    ProductDTO addProduct(@RequestBody ProductDTO product) {
        Cone existingCone = this.coneRepository.findById(product.cone().id());
        Flavor existingFlavor = this.flavorRepository.findById(product.flavor().id());
        List<Topping> existingToppings = (List<Topping>) this.toppingRepository.findAllById(product.toppings().stream().map(ToppingDTO::id).toList());
        Product entity = Product.builder()
                .name(product.name())
                .cone(existingCone)
                .flavor(existingFlavor)
                .toppings(existingToppings)
                .build();
        entity = this.productRepository.save(entity);
        return entity.toDTO();
    }

    @GetMapping("/menu-entry/{id}")
    ProductDTO getProduct(@PathVariable Integer id) {
        return this.productRepository.findById(id).map(Product::toDTO).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatusCode.valueOf(404));
        });
    }

    @DeleteMapping("/menu-entry/{id}")
    void deleteProduct(@PathVariable Integer id) {
        this.productRepository.deleteById(id);
    }

}
