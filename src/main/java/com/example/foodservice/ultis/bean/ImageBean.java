package com.example.foodservice.ultis.bean;

import lombok.Data;

/**
 * Created by NhanNguyen on 5/10/2021
 *
 * @author: NhanNguyen
 * @date: 5/10/2021
 */
@Data
public class ImageBean {
    private int id;
    private String guidProduct;
    private String guidCategory;
    private String guidUser;
    private String name;
    private String imageUrl;
    private String imageId;
}
