package com.example.foodservice.ultis.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by NhanNguyen on 4/22/2021
 *
 * @author: NhanNguyen
 * @date: 4/22/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
    private Integer id;
    private String guid;
    private String username;
    private String password;
    private String avatar;
    private int age;
    private String email;
    private String address;
    private String phoneNumber;
    private boolean status;
    private String firstName;
    private String lastName;
    private String picture;
    private Date dob;
    private String gender;
    private boolean enable;
    private Date createDate;
}
