package com.example.foodservice.data.service;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.repository.OptionIceRepository;
import com.example.foodservice.ultis.bean.OptionIceBean;
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
public class OptionIceService {

    @Autowired
    private VfData vfData;

    @Autowired
    private OptionIceRepository optionIceDAO;

    public List<OptionIceBean> findAllOptionIce(){
        return optionIceDAO.findOptionIceAll(vfData);
    }
}
