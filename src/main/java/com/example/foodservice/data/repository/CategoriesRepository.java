package com.example.foodservice.data.repository;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.Category;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.CategoryBean;
import com.example.foodservice.ultis.form.CategoryForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public interface CategoriesRepository extends CrudRepository<Category, Integer> {
    public default DataTableResults<CategoryBean> getDatatable(VfData vfData, CategoryForm form) {
        List<Object> paramList = new ArrayList<>();

        String sql = "select c.name as name, " +
                " c.description as description, " +
                " c.is_current as current, " +
                " c.created_date as createdDate " +
                " from category c ";

        StringBuilder strCondition = new StringBuilder(" WHERE 1 = 1 ");
        if (!CommonUtil.isEmpty(form.getName())){
            CommonUtil.filter(form.getName(), strCondition, paramList, "c.name");
        }
//        if (!CommonUtil.isEmpty(form.isCurrent())){
//            CommonUtil.filter(form.isCurrent(), strCondition, paramList, "c.is_current");
//        }
        String orderBy = " ORDER BY c.created_date DESC ";
        return vfData.findPaginationQuery(sql + strCondition.toString(),orderBy, paramList, CategoryBean.class);
    }

    ;
}
