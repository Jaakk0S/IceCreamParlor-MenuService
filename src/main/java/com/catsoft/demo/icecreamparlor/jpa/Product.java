package com.catsoft.demo.icecreamparlor.jpa;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import com.catsoft.demo.icecreamparlor.dto.ProductDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "PRODUCT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CONE", referencedColumnName = "ID")
    private Cone cone;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "FLAVOR", referencedColumnName = "ID")
    private Flavor flavor;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "PRODUCT_TOPPINGS",
        joinColumns = @JoinColumn(name = "PRODUCT_ID"),
        inverseJoinColumns = @JoinColumn(name = "TOPPING_ID")
    )
    private List<Topping> toppings;

    public ProductDTO toDTO(boolean omitToppings) {
        return new ProductDTO(
                this.getId(),
                this.getName(),
                new ConeDTO(
                        this.getCone().getId(),
                        this.getCone().getName()
                ),
                new FlavorDTO(
                        this.getFlavor().getId(),
                        this.getFlavor().getName()
                ),
                omitToppings ? null : this.getToppings().stream().map(Topping::toDTO).toList()
        );
    }
}
