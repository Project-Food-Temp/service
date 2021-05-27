package com.example.foodservice.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@Getter
@Setter
@Entity(name = "option_topping")
public class OptionTopping {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "guid",unique = true)
    private String guid;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;
}
