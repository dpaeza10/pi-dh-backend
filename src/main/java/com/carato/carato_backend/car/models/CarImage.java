package com.carato.carato_backend.car.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "images")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(nullable = false)
    private String url;
}
