package com.catsoft.demo.icecreamparlor.jpa;

import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "TOPPING")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy="toppings")
    private List<Product> products;

    public ToppingDTO toDTO() {
        return new ToppingDTO(
                this.getId(),
                this.getName(),
                this.products == null ? null : this.products.stream().map(p -> p.toDTO(true)).toList()
        );
    }
}
