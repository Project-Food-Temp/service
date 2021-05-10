package com.example.foodservice.ultis.bo;

import lombok.Data;

import java.util.Date;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Data
public class ProductBean {
    private int id;
    private int idCategory;
    private String nameProduct;
    private String picture;
    private Double price;
    private Long quantity;
    private boolean isCurrent;
    private String description;
    private Date createdDate;
}
