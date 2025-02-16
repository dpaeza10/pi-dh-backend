package com.carato.carato_backend.car.services;

import com.carato.carato_backend.car.models.CarCategory;
import com.carato.carato_backend.car.repositories.CarCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarCategoryService {
    @Autowired
    private CarCategoryRepository categoryRepository;

    public List<CarCategory> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<CarCategory> getById(int id) {
        return categoryRepository.findById(id);
    }

    public Optional<CarCategory> getByName(String name) {
        return categoryRepository.findByName(name);
    }
}
