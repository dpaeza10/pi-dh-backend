package com.carato.carato_backend.car.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "brake_systems")
@Getter
@Setter
public class CarBrakeSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
}
