package com.example.foodservice.data.repository;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.Product;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.ProductBean;
import com.example.foodservice.ultis.form.CategoryForm;
import com.example.foodservice.ultis.form.ProductForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public interface ProductRepository extends CrudRepository<Product,Integer> {
    List<Product> findAllByGuidCategory(String guidCategory);
    void deleteAllByGuidCategory(String guidCategory);


    public default DataTableResults<ProductBean> getDatatable(VfData vfData, ProductForm form){
        List<Object> paramList = new ArrayList<>();
        String sql = "  SELECT ";
        sql += "    p.id as id, ";
        sql += "    p.name_product as name, ";
        sql += "    p.price as price, ";
        sql += "    p.quantity as quantity, ";
        sql += "    p.is_current as current, ";
        sql += "    p.description as description, ";
        sql += "    p.created_date as createdDate, ";
        sql += "    c.name as category, ";
        sql += "    c.guid as guidCategory ";
        sql += "    FROM product p ";
        sql += "    JOIN image img ON img.guid_product = p.guid ";
        sql += "    JOIN category c ON c.guid = p.guid_category ";

        StringBuilder strCondition = new StringBuilder(" WHERE 1 = 1");

        String orderBy = " ORDER BY p.created_date DESC ";
        return vfData.findPaginationQuery(sql + strCondition.toString(),orderBy,paramList,ProductBean.class);
    }
}
