package com.example.foodservice.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Getter
@Setter
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_category")
    private int idCategory;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "picture")
    private String picture;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "is_current")
    private boolean isCurrent;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Date createdDate;
}
