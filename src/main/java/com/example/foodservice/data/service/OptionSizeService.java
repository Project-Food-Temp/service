package com.example.foodservice.data.service;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.repository.OptionSizeRepository;
import com.example.foodservice.ultis.bean.OptionIceBean;
import com.example.foodservice.ultis.bean.OptionSizeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@Service
public class OptionSizeService {
    @Autowired
    private VfData vfData;
    @Autowired
    private OptionSizeRepository optionSizeDAO;

    public List<OptionSizeBean> getOptionSize(){
        return optionSizeDAO.getOptionSizeAll(vfData);
    }

}
