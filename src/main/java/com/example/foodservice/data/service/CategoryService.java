package com.example.foodservice.data.service;

import com.example.foodservice.data.entity.Category;
import com.example.foodservice.data.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Service
public class CategoryService {
    @Autowired
    private EntityManager entityManager;

//    public List<Category>

}
