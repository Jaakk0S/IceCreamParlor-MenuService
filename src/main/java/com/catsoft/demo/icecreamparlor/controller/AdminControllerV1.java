package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import com.catsoft.demo.icecreamparlor.dto.ProductDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import com.catsoft.demo.icecreamparlor.repository.ConeRepository;
import com.catsoft.demo.icecreamparlor.repository.FlavorRepository;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import com.catsoft.demo.icecreamparlor.repository.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/admin/v1")
public class AdminControllerV1 {

    @Autowired
    private ConeRepository coneRepository;

    @Autowired
    private FlavorRepository flavorRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired private
    ProductRepository productRepository;


    
    @GetMapping("/menu-entry")
    List<ProductDTO> getAllProducts() {
        return null;
    }

    @PutMapping("/menu-entry")
    ProductDTO addProduct() {
        return null;
    }

    @GetMapping("/menu-entry/{id}")
    ProductDTO getProduct(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/menu-entry/{id}")
    ProductDTO addProduct(@PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/menu-entry/{id}")
    void deleteProduct(@PathVariable Integer id) {

    }



    @GetMapping("/cone")
    List<ConeDTO> getAllCones() {
        return null;
    }

    @PutMapping("/cone")
    ConeDTO addCone() {
        return null;
    }

    @GetMapping("/cone/{id}")
    ConeDTO getCone(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/cone/{id}")
    ConeDTO addCone(@PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/cone/{id}")
    void deleteCone(@PathVariable Integer id) {

    }



    @GetMapping("/flavor")
    List<FlavorDTO> getAllFlavors() {
        return null;
    }

    @PutMapping("/flavor")
    FlavorDTO addFlavor() {
        return null;
    }

    @GetMapping("/flavor/{id}")
    FlavorDTO getFlavor(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/flavor/{id}")
    FlavorDTO addFlavor(@PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/flavor/{id}")
    void deleteFlavor(@PathVariable Integer id) {

    }



    @GetMapping("/topping")
    List<ToppingDTO> getAllToppings() {
        return null;
    }

    @PutMapping("/topping")
    ToppingDTO addTopping() {
        return null;
    }

    @GetMapping("/topping/{id}")
    ToppingDTO getTopping(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/topping/{id}")
    ToppingDTO addTopping(@PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/topping/{id}")
    void deleteTopping(@PathVariable Integer id) {

    }
}
