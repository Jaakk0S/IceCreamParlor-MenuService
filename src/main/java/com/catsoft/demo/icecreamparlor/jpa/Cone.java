package com.catsoft.demo.icecreamparlor.jpa;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "CONE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public ConeDTO toDTO() {
        return new ConeDTO(this.getId(), this.getName());
    }
}


