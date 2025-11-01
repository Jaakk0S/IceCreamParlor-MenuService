package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import com.catsoft.demo.icecreamparlor.jpa.Flavor;
import com.catsoft.demo.icecreamparlor.jpa.Product;
import com.catsoft.demo.icecreamparlor.jpa.Topping;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import com.catsoft.demo.icecreamparlor.repository.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/admin/v1")
public class ToppingController {


    /*
        NOTE: Because topping API is a simple CRUD API, there is no service layer.
     */


    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private ProductRepository productRepository;



    @GetMapping("/topping")
    List<ToppingDTO> getAllToppings() {
        return StreamSupport.stream(this.toppingRepository.findAll().spliterator(), false).map(Topping::toDTO).toList();
    }

    @PostMapping("/topping")
    ToppingDTO addTopping(@RequestBody ToppingDTO topping) {
        Topping entity = Topping.builder().name(topping.name()).build();
        entity = this.toppingRepository.save(entity);
        return entity.toDTO();
    }

    @GetMapping("/topping/{id}")
    ToppingDTO getTopping(@PathVariable Integer id) {
        return this.toppingRepository.findById(id).map(Topping::toDTO).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatusCode.valueOf(404));
        });
    }

    @DeleteMapping("/topping/{id}")
    void deleteTopping(@PathVariable Integer id) {
        this.toppingRepository.findById(id).ifPresentOrElse(
                t -> {
                    List<Product> productsWithTopping = StreamSupport.stream(this.productRepository.findAll().spliterator(),
                            false).filter(p -> p.getToppings().stream().anyMatch(t2 -> t2.getId() == id)).toList();
                    if (productsWithTopping.isEmpty())
                        this.toppingRepository.deleteById(id);
                    else
                        throw new ResponseStatusException(HttpStatusCode.valueOf(409));
                },
                () -> { throw new ResponseStatusException(HttpStatusCode.valueOf(204)); }
        );
    }
}
