package com.catsoft.demo.icecreamparlor.controller;

import com.catsoft.demo.icecreamparlor.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/menu/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order/{id}")
    void placeOrder(@PathVariable Integer id) {

    }

    @PostMapping("/place-custom-order")
    void placeCustomOrder() {

    }
}
