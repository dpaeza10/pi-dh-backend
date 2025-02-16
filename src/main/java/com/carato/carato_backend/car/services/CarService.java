package com.carato.carato_backend.car.services;

import com.carato.carato_backend.car.CarDTO;
import com.carato.carato_backend.car.models.*;
import com.carato.carato_backend.car.repositories.CarRepository;
import com.carato.carato_backend.exceptions.GeneralException;
import com.carato.carato_backend.image.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarCategoryService carCategoryService;
    @Autowired
    private CarExtraService carExtraService;
    @Autowired
    private ImageUtil imageUtil;

    public List<Car> getAll(int page, int size) {
        return carRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Car> getAllByCategoryId(int categoryId, int page, int size) {
        return carRepository.findAllByCategoryId(categoryId, PageRequest.of(page, size)).getContent();
    }

    public List<Car> getAllByRandomOrder(int limit) {
        return carRepository.findAllByRandomOrder(limit);
    }

    public List<Car> getAllByCategoryIdByRandomOrder(int categoryId, int limit) {
        return carRepository.findAllByCategoryByIdRandomOrder(categoryId, limit);
    }

    public Optional<Car> getById(int id) {
        return carRepository.findById(id);
    }

    public Optional<Car> getByName(String name) {
        return carRepository.findByName(name);
    }

    public boolean existsByName(String name) {
        return carRepository.existsByName(name);
    }

    public Car save(CarDTO carDTO) throws IOException {
        List<MultipartFile> files = carDTO.getImages();
        if (files == null || files.isEmpty()) {
            throw new GeneralException(400, "The images are required");
        }

        if (files.size() > 10) {
            throw new GeneralException(400, "Upload 1-10 images");
        }

        String name = carDTO.getName();
        if (existsByName(name)) {
            throw new GeneralException(409, "Duplicate name '" + name + "'");
        }

        Car car = new Car();
        updateCarFromDTO(car, carDTO);

        List<CarImage> imagesUrl = imageUtil.saveImage(files).stream().map(url -> new CarImage(null, url)).toList();
        car.setImages(imagesUrl);

        return carRepository.save(car);
    }

    public Car update(int id, CarDTO carDTO) throws IOException {
        List<MultipartFile> files = carDTO.getImages();
        if (files != null && files.size() > 10) {
            throw new GeneralException(400, "Upload 1-10 images");
        }

        String name = carDTO.getName();
        if (existsByName(name)) {
            throw new GeneralException(409, "Duplicate name '" + name + "'");
        }

        Car car = getById(id).orElseThrow(() ->
                new GeneralException(404, "Car not found")
        );

        updateCarFromDTO(car, carDTO);

        if (files != null && !files.isEmpty()) {
            // TODO: When we use Amazon S3, the old images should be removed
            // List<CarImage> oldImages = car.getImages().stream().toList();

            List<CarImage> newImagesUrl = imageUtil.saveImage(files).stream().map(url -> new CarImage(null, url)).toList();
            car.setImages(newImagesUrl);
        }

        return carRepository.save(car);
    }

    private void updateCarFromDTO(Car car, CarDTO carDTO) {
        car.setName(carDTO.getName());
        car.setDescription(carDTO.getDescription());
        car.setPrice(carDTO.getPrice());
        car.setMileage(carDTO.getMileage());
        car.setYear(carDTO.getYear());
        car.setHasAirCondition(Boolean.parseBoolean(carDTO.getHasAirCondition()));
        car.setHorsePower(carDTO.getHorsePower());
        car.setCapacity(carDTO.getCapacity());
        car.setDoors(carDTO.getDoors());

        CarBrakeSystem brakeSystem = carExtraService
                .getBrakeSystemById(carDTO.getBrakeSystemId())
                .orElseThrow(() -> new GeneralException(400, "The brakeSystem specified wasn't found"));
        CarBrand brand = carExtraService
                .getBrandById(carDTO.getBrandId())
                .orElseThrow(() -> new GeneralException(400, "The brand specified wasn't found"));
        CarCategory category = carCategoryService
                .getById(carDTO.getCategoryId())
                .orElseThrow(() -> new GeneralException(400, "The category specified wasn't found"));
        CarGasoline gasoline = carExtraService
                .getGasolineById(carDTO.getGasolineId())
                .orElseThrow(() -> new GeneralException(400, "The gasoline specified wasn't found"));
        CarTransmission transmission = carExtraService
                .getTransmissionById(carDTO.getTransmissionId())
                .orElseThrow(() -> new GeneralException(400, "The transmission specified wasn't found"));

        car.setBrakeSystem(brakeSystem);
        car.setBrand(brand);
        car.setCategory(category);
        car.setGasoline(gasoline);
        car.setTransmission(transmission);
    }

    public void deleteCarById(int id) throws GeneralException {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new GeneralException(404, "Car not found");
        }
    }
}
