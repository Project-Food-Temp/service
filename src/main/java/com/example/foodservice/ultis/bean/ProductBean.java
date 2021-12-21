package com.example.foodservice.ultis.bean;

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
    private Integer id;
    private String category;
    private String guidCategory;
    private String name;
    private String picture;
    private Double price;
    private Long quantity;
    private Boolean current;
    private String description;
    private Date createdDate;
}
