package com.example.foodservice.ultis.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by NhanNguyen on 4/28/2021
 *
 * @author: NhanNguyen
 * @date: 4/28/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserBean {
    private int id;
    private int roleId;
    private int userId;

}
