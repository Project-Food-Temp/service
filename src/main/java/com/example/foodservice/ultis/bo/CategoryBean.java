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
public class CategoryBean {
    private int id;
    private String name;
    private String description;
    private boolean isCurrent;
    private Date createdDate;


}
