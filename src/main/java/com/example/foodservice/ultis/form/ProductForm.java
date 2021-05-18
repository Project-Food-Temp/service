package com.example.foodservice.ultis.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Data
public class ProductForm {
    private int id;
    private String guidCategory;
    private String nameProduct;
    private String picture;
    private Double price;
    private Long quantity;
    private boolean isCurrent;
    private String description;
    private Date createdDate;
    private MultipartFile[] fileImages;
}
