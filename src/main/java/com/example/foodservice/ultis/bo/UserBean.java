package com.example.foodservice.ultis.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String name;
    private String username;
    private String password;
    private String avatar;
    private int age;
    private String email;
    private boolean gender;
    private String address;
    private String phoneNumber;
    private boolean status;
}
