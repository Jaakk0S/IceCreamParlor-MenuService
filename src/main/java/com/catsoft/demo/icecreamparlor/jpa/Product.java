package com.catsoft.demo.icecreamparlor.jpa;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {

    @Id
    private int id;

    private String name;

    @ManyToOne
    private Cone cone;

    @ManyToOne
    private Flavor flavor;

    @ManyToMany
    @JoinTable(
        name = "PRODUCT_TOPPINGS",
        joinColumns = @JoinColumn(name = "PRODUCT_ID"),
        inverseJoinColumns = @JoinColumn(name = "TOPPING_ID")
    )
    private List<Topping> toppings;
}
