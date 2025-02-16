package com.carato.carato_backend.car.controllers;

import com.carato.carato_backend.car.CarDTO;
import com.carato.carato_backend.car.CarResponse;
import com.carato.carato_backend.car.models.Car;
import com.carato.carato_backend.car.services.CarService;
import com.carato.carato_backend.exceptions.GeneralException;
import com.carato.carato_backend.responses.MessageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarResponse> showCars(
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
            return carService.getAllByRandomOrder(size).stream().map(CarResponse::new).toList();
        } else {
            return carService.getAll(page, size).stream().map(CarResponse::new).toList();
        }
    }

    @GetMapping("/{value}")
    public CarResponse showCarByIdOrName(@PathVariable String value) {
        boolean isAlphanumeric = value.matches("^[a-zA-Z0-9_À-ÿ]+$");
        boolean isNumeric = value.matches("^[0-9]+$");

        if (isNumeric) {
            int id = Integer.parseInt(value);
            return showCarById(id);
        } else if (isAlphanumeric) {
            return showCarByName(value);
        } else {
            throw new GeneralException(400, "Invalid parameter format");
        }
    }

    private CarResponse showCarByName( String name) {
        String nameWithoutDashes = name.replace("_", " ");

        Car car = carService.getByName(nameWithoutDashes).orElseThrow(() ->
                new GeneralException(404, "Car not found")
        );

        return new CarResponse(car);
    }

    private CarResponse showCarById( int id) {
        Car car = carService.getById(id).orElseThrow(() ->
                new GeneralException(404, "Car not found")
        );

        return new CarResponse(car);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CarResponse postCar(@Valid CarDTO carDTO) {
        try {
            return new CarResponse(carService.save(carDTO));
        } catch (IOException e) {
            throw new GeneralException(500, "Problems while uploading images");
        }
    }

    @PutMapping("/{id}")
    public CarResponse updateCar(@PathVariable int id, @Valid CarDTO carDTO) {
        try {
            return new CarResponse(carService.update(id, carDTO));
        } catch (IOException e) {
            throw new GeneralException(500, "Problems while uploading images");
        }
    }

    @DeleteMapping("/{id}")
    public MessageResponse<String> deleteCarById(@PathVariable int id) {
        carService.deleteCarById(id);
        return new MessageResponse<>("ok", "Car deleted");
    }
}
