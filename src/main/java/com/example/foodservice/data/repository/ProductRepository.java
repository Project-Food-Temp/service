package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
}
