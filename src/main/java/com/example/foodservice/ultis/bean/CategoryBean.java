package com.example.foodservice.ultis.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer id;
    private String guid;
    private String name;
    private String description;
    private String urlImg;
    private String fileName;
    private Boolean current;
    private Date createdDate;


}
