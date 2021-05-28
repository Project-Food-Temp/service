package com.example.foodservice.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by NhanNguyen on 4/28/2021
 *
 * @author: NhanNguyen
 * @date: 4/28/2021
 */
@Getter
@Setter
@Entity(name = "permission_role")
public class PermissionRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "permission_id")
    private int permissionId;

    @Column(name = "role_id")
    private int userId;
}
