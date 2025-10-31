package com.catsoft.demo.icecreamparlor.jpa;

import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
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

    public ToppingDTO toDTO() {
        return new ToppingDTO(this.getId(), this.getName());
    }
}
