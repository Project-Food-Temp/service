package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    Optional<Image> findImageByImageId(String imageId);

    void deleteByImageId(String imageId);

    Optional<Image> findByGuidCategory(String guid);
}
