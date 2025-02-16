package com.carato.carato_backend.car;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CarDTO {
    @NotBlank(message = "Name must be required")
    @Pattern(message = "Name must be alphanumeric with spaces or accents", regexp = "^[a-zA-Z0-9\\sÀ-ÿ]+$")
    private String name;
    @NotBlank(message = "Description must be required")
    private String description;
    @NotNull(message = "Price must be required")
    @Positive(message = "Price must be greater than 0")
    @Digits(message = "Price must have a maximum of two decimals", integer = 10, fraction = 2)
    private Float price;
    @NotNull(message = "Mileage must be required")
    @Positive(message = "Mileage must be greater than 0")
    private Integer mileage;
    @NotNull(message = "Year must be required")
    @Positive(message = "Year must be greater than 0")
    private Integer year;
    @NotBlank(message = "HasAirCondition must be required")
    private String hasAirCondition;
    @NotNull(message = "HorsePower must be required")
    @Positive(message = "HorsePower must be greater than 0")
    private Integer horsePower;
    @NotNull(message = "Capacity must be required")
    @Positive(message = "Capacity must be greater than 0")
    private Integer capacity;
    @NotNull(message = "Doors must be required")
    @Positive(message = "Doors must be greater than 0")
    private Integer doors;

    private List<MultipartFile> images;

    @NotNull(message = "The brakeSystemId must be required")
    @Positive(message = "The brakeSystemId must be greater than 0")
    private Integer brakeSystemId;
    @NotNull(message = "The brandId must be required")
    @Positive(message = "The brandId must be greater than 0")
    private Integer brandId;
    @NotNull(message = "The categoryId must be required")
    @Positive(message = "The categoryId must be greater than 0")
    private Integer categoryId;
    @NotNull(message = "The gasolineId must be required")
    @Positive(message = "The gasolineId must be greater than 0")
    private Integer gasolineId;
    @NotNull(message = "The transmissionId must be required")
    @Positive(message = "The transmissionId must be greater than 0")
    private Integer transmissionId;

    // This is necessary, because SpringBoot adds non-existent file in some cases
    public void setImages(List<MultipartFile> images) {
        this.images = images.stream().filter(file -> !file.isEmpty()).toList();
    }
}
