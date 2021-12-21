package com.example.foodservice.data.repository;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.OptionSize;
import com.example.foodservice.ultis.bean.OptionSizeBean;
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
public interface OptionSizeRepository extends CrudRepository<OptionSize, Integer> {

    boolean existsOptionSizeByName(String name);

    OptionSize findOptionSizeByName(String name);
    public default List<OptionSizeBean> getOptionSizeAll(VfData vfData) {
        String sql = "  SELECT oz.id as id, ";
        sql +=  "   oz.guid as guid, ";
        sql +=  "   oz.name as name, ";
        sql +=  "   oz.extra_price as extraPrice ";
        sql +=  "   FROM option_size oz";
        sql +=  "   ORDER BY oz.id";
        return vfData.findAllByQuery(sql, OptionSizeBean.class);
    }
}
