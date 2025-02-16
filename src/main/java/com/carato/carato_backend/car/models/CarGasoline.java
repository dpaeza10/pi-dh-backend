package com.carato.carato_backend.car.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "gasolines")
@Getter
@Setter
public class CarGasoline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
}
