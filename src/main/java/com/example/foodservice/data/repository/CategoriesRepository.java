package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoriesRepository extends CrudRepository<Category,Integer> {
}
