package com.example.foodservice.controls.option_controls;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.data.entity.OptionSize;
import com.example.foodservice.data.repository.OptionSizeRepository;
import com.example.foodservice.data.service.OptionSizeService;
import com.example.foodservice.ultis.form.OptionSizeForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Nhan Nguyen on 5/28/2021
 *
 * @author Nhan Nguyen
 * @date 5/28/2021
 */
@RestController
@RequestMapping("/options/size")
public class OptionSizeControl {
    private static final Logger log = LoggerFactory.getLogger(OptionIceControl.class);

    @Autowired
    private OptionSizeService optionSizeService;

    @Autowired
    private OptionSizeRepository optionSizeDAO;

    @PostMapping
    public @ResponseBody
    Response saveOrUpdate(OptionSizeForm form) {
        OptionSize optionSize;
        if (CommonUtil.isEmpty(form.getId())) {
            //insert
            if (optionSizeDAO.existsOptionSizeByName(form.getName())){
                return Response.error(Constants.RESPONSE_CODE.EXISTS_OPTION);
            }
            optionSize = new OptionSize();
            optionSize.setGuid(UUID.randomUUID().toString());
        } else {
            //update
            optionSize = optionSizeDAO.findOptionSizeByName(form.getName());
        }
        optionSize.setName(form.getName());
        optionSize.setExtraPrice(CommonUtil.NVL(form.getExtraPrice()));
        optionSizeDAO.save(optionSize);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

    @GetMapping("/find_all")
    public @ResponseBody Response findAllOptionSize(){
        return Response.success(Constants.RESPONSE_CODE.SUCCESS)
                .withData(optionSizeService.getOptionSize());
    }

    @GetMapping("/{id}")
    public @ResponseBody Response findOptionSizeById(@PathVariable int id){
        OptionSize optionSize = optionSizeDAO.findById(id).orElse(null);
        if (CommonUtil.isEmpty(optionSize)){
            return Response.error(Constants.RESPONSE_CODE.RECORD_DELETED);
        }
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(optionSize);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody Response deleteOptionSizeById(@PathVariable int id){
        OptionSize optionSize = optionSizeDAO.findById(id).orElse(null);
        if (CommonUtil.isEmpty(optionSize)){
            return Response.error(Constants.RESPONSE_CODE.RECORD_DELETED);
        }
        optionSizeDAO.deleteById(id);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }
}
