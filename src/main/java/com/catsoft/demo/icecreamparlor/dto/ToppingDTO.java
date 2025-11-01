package com.catsoft.demo.icecreamparlor.dto;

import java.util.List;

public record ToppingDTO(int id, String name, List<ProductDTO> products) {}