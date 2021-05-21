package com.example.foodservice.ultis.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryBean {
    private int id;
    private String name;
    private String description;
    private Boolean current;
    private Date createdDate;


}
