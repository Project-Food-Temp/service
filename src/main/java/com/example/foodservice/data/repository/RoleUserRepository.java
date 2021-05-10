package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.RoleUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

public interface RoleUserRepository extends CrudRepository<RoleUser,Integer> {
//    Iterator<RoleUser> findRoleUserBy
}
