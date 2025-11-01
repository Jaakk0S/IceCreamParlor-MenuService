package com.catsoft.demo.icecreamparlor.jpa;

import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "FLAVOR")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy="flavor")
    private List<Product> products;

    public FlavorDTO toDTO() {
        return new FlavorDTO(this.getId(), this.getName());
    }
}
