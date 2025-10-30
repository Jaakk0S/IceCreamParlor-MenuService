package com.catsoft.demo.icecreamparlor.dto;

import com.catsoft.demo.icecreamparlor.jpa.Cone;

import java.util.List;

public record ProductDTO(int id, String name, Cone cone, FlavorDTO flavor, List<ToppingDTO> toppings) {}
