package com.example.foodservice.data.service;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.Category;
import com.example.foodservice.data.repository.CategoriesRepository;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.CategoryBean;
import com.example.foodservice.ultis.form.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Service

public class CategoryService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VfData vfData;

    @Autowired
    private CategoriesRepository categoriesRepository;
//    public List<Category>

    public DataTableResults<CategoryBean> getDataTables(CategoryForm form){
        DataTableResults<CategoryBean> dtTable = categoriesRepository.getDatatable(vfData,form);
//        List<CategoryBean> lst = dtTable.getData();
        return dtTable;
    }

}
