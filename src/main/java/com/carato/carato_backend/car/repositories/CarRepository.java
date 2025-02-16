package com.carato.carato_backend.car.repositories;

import com.carato.carato_backend.car.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Page<Car> findAllByCategoryId(int categoryId, Pageable pageable);
    // FIXME: This random order methods aren't efficient on a large database
    @Query(nativeQuery = true, value = "SELECT * FROM cars ORDER BY RAND() LIMIT ?1")
    List<Car> findAllByRandomOrder(int limit);
    @Query(nativeQuery = true, value = "SELECT * FROM cars WHERE category_id=?1 ORDER BY RAND() LIMIT ?2")
    List<Car> findAllByCategoryByIdRandomOrder(int categoryId, int limit);
    Optional<Car> findByName(String name);
    boolean existsByName(String name);
}
