package com.carato.carato_backend.car.controllers;

import com.carato.carato_backend.car.CarResponse;
import com.carato.carato_backend.car.models.Car;
import com.carato.carato_backend.car.models.CarCategory;
import com.carato.carato_backend.car.services.CarCategoryService;
import com.carato.carato_backend.car.services.CarService;
import com.carato.carato_backend.exceptions.GeneralException;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CarCategoryController {
    @Autowired
    private CarCategoryService categoryService;
    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarCategory> showAllCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/{value}")
    public CarCategory showCarByIdOrName(@PathVariable String value) {
        boolean isAlphanumeric = value.matches("^[a-zA-Z0-9_À-ÿ]+$");
        boolean isNumeric = value.matches("^[0-9]+$");

        if (isNumeric) {
            int id = Integer.parseInt(value);
            return showCategoryById(id);
        } else if (isAlphanumeric) {
            return showCategoryByName(value);
        } else {
            throw new GeneralException(400, "Invalid parameter format");
        }
    }

    private CarCategory showCategoryByName(String name) {
        String nameWithoutDashes = name.replace("_", " ");

        return categoryService.getByName(nameWithoutDashes).orElseThrow(() ->
                new GeneralException(404, "Category not found")
        );
    }

    private CarCategory showCategoryById(int id) {
        return categoryService.getById(id).orElseThrow(() ->
                new GeneralException(404, "Category not found")
        );
    }

    @GetMapping("/{id}/cars")
    public List<CarResponse> showCarsByCategoryId(
            @PathVariable
            int id,
            @RequestParam(required = false, defaultValue = "false")
            boolean random,
            @Positive
            @RequestParam(required = false, defaultValue = "1")
            int page,
            @Positive
            @RequestParam(required = false, defaultValue = "1000")
            int size
    ) {
        page = page - 1;

        if (random) {
            return carService.getAllByCategoryIdByRandomOrder(id, size).stream().map(CarResponse::new).toList();
        } else {
            return carService.getAllByCategoryId(id, page, size).stream().map(CarResponse::new).toList();
        }
    }
}
