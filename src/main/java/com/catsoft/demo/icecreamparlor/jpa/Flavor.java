package com.catsoft.demo.icecreamparlor.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Flavor {

    @Id
    private int id;

    private String name;
}
