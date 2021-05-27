package com.example.foodservice.data.repository;

import com.example.foodservice.data.entity.OptionSize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@Transactional
public interface OptionSizeRepository extends CrudRepository<OptionSize,Integer> {
}
