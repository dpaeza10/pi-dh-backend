package com.carato.carato_backend.car.controllers;

import com.carato.carato_backend.car.services.CarExtraService;
import com.carato.carato_backend.car.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarExtraController {
    @Autowired
    private CarExtraService carExtraService;

    @GetMapping("brake-systems")
    public List<CarBrakeSystem> showBrakeSystems() {
        return carExtraService.getBrakeSystems();
    }

    @GetMapping("brands")
    public List<CarBrand> showBrands() {
        return carExtraService.getBrands();
    }

    @GetMapping("gasolines")
    public List<CarGasoline> showGasolines() {
        return carExtraService.getGasolines();
    }

    @GetMapping("transmissions")
    public List<CarTransmission> showTransmissions() {
        return carExtraService.getTransmissions();
    }
}
