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
@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "guid",unique = true)
    private String guid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_current")
    private Boolean current;

    @Column(name = "created_date")
    private Date createdDate;
}
