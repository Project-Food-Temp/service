package com.example.foodservice.data.repository;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.OptionTopping;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.OptionToppingBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@Transactional
public interface OptionToppingRepository extends CrudRepository<OptionTopping,Integer> {

    boolean existsOptionToppingByName(String name);

    boolean existsOptionToppingById(int id);

    OptionTopping findOptionToppingByName(String name);

    public default DataTableResults<OptionToppingBean> getDatatable(VfData vfData){
        List<Object> paramList = new ArrayList<>();
        String sql = "  SELECT ";
        sql +=  "   ot.id as id, ";
        sql +=  "   ot.guid as guid, ";
        sql +=  "   ot.name as name, ";
        sql +=  "   ot.price as price ";
        sql +=  "   FROM option_topping ot";

        String orderBy = " ORDER BY ot.id DESC ";
        return vfData.findPaginationQuery(sql,orderBy,paramList,OptionToppingBean.class);
    }
}
