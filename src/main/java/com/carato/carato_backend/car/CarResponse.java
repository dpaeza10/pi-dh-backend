package com.carato.carato_backend.car;

import com.carato.carato_backend.car.models.Car;
import com.carato.carato_backend.car.models.CarImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarResponse {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer mileage;
    private Integer year;
    private Boolean hasAirCondition;
    private Integer horsePower;
    private Integer capacity;
    private Integer doors;
    private String brakeSystem;
    private String brand;
    private String category;
    private String gasoline;
    private List<CarImage> images;
    private String transmission;

    public CarResponse(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.description = car.getDescription();
        this.price = car.getPrice();
        this.mileage = car.getMileage();
        this.year = car.getYear();
        this.hasAirCondition = car.getHasAirCondition();
        this.horsePower = car.getHorsePower();
        this.capacity = car.getCapacity();
        this.doors = car.getDoors();

        this.brakeSystem = car.getBrakeSystem().getName();
        this.brand = car.getBrand().getName();
        this.category = car.getCategory().getName();
        this.gasoline = car.getGasoline().getName();
        this.images = car.getImages();
        this.transmission = car.getTransmission().getName();
    }
}
