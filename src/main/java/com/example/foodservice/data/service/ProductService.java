package com.example.foodservice.data.service;

import com.example.foodservice.control.CategoryControl;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.entity.Product;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.data.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;


    public void delLstProduct(List<Product> lstProduct) {
        lstProduct.forEach(this::delProduct);
    }

    public void delProduct(Product product) {
        List<Image> lstImgProduct = imageRepository.findByGuidProduct(product.getGuid());
        lstImgProduct.forEach(img -> {
            imageService.delete(img.getImageId());
        });
        productRepository.delete(product);
    }

}
