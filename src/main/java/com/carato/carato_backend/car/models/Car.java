package com.carato.carato_backend.car.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private Integer mileage;
    @Column(nullable = false)
    private Integer year;
    @Column(nullable = false)
    private Boolean hasAirCondition;
    @Column(nullable = false)
    private Integer horsePower;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private Integer doors;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CarBrakeSystem brakeSystem;
    @ManyToOne
    @JoinColumn(nullable = false)
    private CarBrand brand;
    @ManyToOne
    @JoinColumn(nullable = false)
    private CarCategory category;
    @ManyToOne
    @JoinColumn(nullable = false)
    private CarGasoline gasoline;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "car_id")
    private List<CarImage> images;
    @ManyToOne
    @JoinColumn(nullable = false)
    private CarTransmission transmission;
}
