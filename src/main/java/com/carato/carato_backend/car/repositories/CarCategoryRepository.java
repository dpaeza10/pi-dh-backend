package com.carato.carato_backend.car.repositories;

import com.carato.carato_backend.car.models.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> {
    Optional<CarCategory> findByName(String name);
}
