package com.example.foodservice.data.service;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.repository.OptionToppingRepository;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.CategoryBean;
import com.example.foodservice.ultis.bean.OptionToppingBean;
import com.example.foodservice.ultis.form.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@Service
public class OptionToppingService {
    @Autowired
    private VfData vfData;
    @Autowired
    private OptionToppingRepository optionToppingDAO;

    public DataTableResults<OptionToppingBean> getDataTables(){
        DataTableResults<OptionToppingBean> dtTable = optionToppingDAO.getDatatable(vfData);
        return dtTable;
    }
}
