package com.example.foodservice.ultis.form;

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
public class UserForm {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String picture;
    private String email;
    private Date dob;
    private boolean gender;
    private String address;
    private String phoneNumber;
    private boolean status;
}
