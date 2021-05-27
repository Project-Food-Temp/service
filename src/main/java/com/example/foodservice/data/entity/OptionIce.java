package com.example.foodservice.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Nhan Nguyen on 5/26/2021
 *
 * @author Nhan Nguyen
 * @date 5/26/2021
 */
@Getter
@Setter
@Entity(name = "option_ice")
public class OptionIce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "guid",unique = true)
    private String guid;

    @Column(name = "option_name")
    private String optionName;

}
