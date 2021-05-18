package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    List<Product> findAllByGuidCategory(String guidCategory);
    void deleteAllByGuidCategory(String guidCategory);
}
