package com.catsoft.demo.icecreamparlor.dto;

import java.util.List;

public record ProductDTO(int id, String name, ConeDTO cone, FlavorDTO flavor, List<ToppingDTO> toppings) {}
