package com.catsoft.demo.icecreamparlor.jpa;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
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
public class Flavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public FlavorDTO toDTO() {
        return new FlavorDTO(this.getId(), this.getName());
    }
}
