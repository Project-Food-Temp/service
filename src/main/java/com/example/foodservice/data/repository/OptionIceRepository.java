package com.example.foodservice.data.repository;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.OptionIce;
import com.example.foodservice.ultis.bean.OptionIceBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@Transactional
public interface OptionIceRepository extends CrudRepository<OptionIce,Integer> {
    OptionIce findOptionIceByOptionName(String optionName);

    public default List<OptionIceBean> findOptionIceAll(VfData vfData){
        String sql = "  SELECT oi.id as id, ";
        sql +=  "   oi.guid as guid, ";
        sql +=  "   oi.option_name as optionName ";
        sql +=  "   FROM option_ice oi";
//        sql +=  "   ORDER BY oz.id";
        return vfData.findAllByQuery(sql,OptionIceBean.class);
    }

}
