package com.catsoft.demo.icecreamparlor.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductDTO(
        int id,
        String name,
        @NotNull ConeDTO cone,
        @NotNull FlavorDTO flavor,
        @NotNull List<ToppingDTO> toppings
) {}
