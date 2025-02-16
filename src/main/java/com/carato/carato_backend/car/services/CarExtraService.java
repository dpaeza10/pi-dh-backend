package com.carato.carato_backend.car.services;

import com.carato.carato_backend.car.models.*;
import com.carato.carato_backend.car.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarExtraService {
    @Autowired
    private CarBrakeSystemRepository brakeSystemRepository;
    @Autowired
    private CarBrandRepository brandRepository;
    @Autowired
    private CarGasolineRepository gasolineRepository;
    @Autowired
    private CarTransmissionRepository transmissionRepository;

    public List<CarBrakeSystem> getBrakeSystems() {
        return brakeSystemRepository.findAll();
    }

    public Optional<CarBrakeSystem> getBrakeSystemById(int id) {
        return brakeSystemRepository.findById(id);
    }

    public List<CarBrand> getBrands() {
        return brandRepository.findAll();
    }

    public Optional<CarBrand> getBrandById(int id) {
        return brandRepository.findById(id);
    }

    public List<CarGasoline> getGasolines() {
        return gasolineRepository.findAll();
    }

    public Optional<CarGasoline> getGasolineById(int id) {
        return gasolineRepository.findById(id);
    }

    public List<CarTransmission> getTransmissions() {
        return transmissionRepository.findAll();
    }

    public Optional<CarTransmission> getTransmissionById(int id) {
        return transmissionRepository.findById(id);
    }
}
