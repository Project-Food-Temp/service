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


    boolean existsCategoriesByName(String name);

    public default DataTableResults<CategoryBean> getDatatable(VfData vfData, CategoryForm form) {
        List<Object> paramList = new ArrayList<>();
        String sql = "SELECT ";
        sql += "    c.id as id, ";
        sql += "    c.guid as guid, ";
        sql += "    c.name as name,";
        sql += "    img.image_url as urlImg,";
        sql += "    c.is_current as current, ";
        sql += "    c.description as description, ";
        sql += "    img.name as fileName, ";
        sql += "    c.created_date as createdDate ";
        sql += "    FROM category c ";
        sql += "    LEFT JOIN image img ON c.guid = img.guid_category ";

        StringBuilder strCondition = new StringBuilder(" WHERE 1 = 1 ");
        if (!CommonUtil.isEmpty(form.getName())) {
            CommonUtil.filter(form.getName(), strCondition, paramList, "c.name");
        }
        String orderBy = " ORDER BY c.created_date DESC ";
        return vfData.findPaginationQuery(sql + strCondition.toString(), orderBy, paramList, CategoryBean.class);
    }
}
