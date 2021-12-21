package com.example.foodservice.controls.option_controls;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.data.entity.OptionTopping;
import com.example.foodservice.data.repository.OptionToppingRepository;
import com.example.foodservice.data.service.OptionToppingService;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.OptionIceBean;
import com.example.foodservice.ultis.bean.OptionToppingBean;
import com.example.foodservice.ultis.form.OptionSizeForm;
import com.example.foodservice.ultis.form.OptionToppingForm;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nhan Nguyen on 5/28/2021
 *
 * @author Nhan Nguyen
 * @date 5/28/2021
 */
@RestController
@RequestMapping("/options/topping")
public class OptionToppingControl {
    private static final Logger log = LoggerFactory.getLogger(OptionToppingControl.class);

    @Autowired
    private OptionToppingService optionToppingService;

    @Autowired
    private OptionToppingRepository optionToppingDAO;

    @GetMapping("/search")
    public @ResponseBody
    DataTableResults<OptionToppingBean> processSearch() {
        return optionToppingService.getDataTables();
    }

    @PostMapping
    public @ResponseBody
    Response saveOrUpdate(OptionToppingForm form) {
        OptionTopping optionTopping;
        if (CommonUtil.isEmpty(form.getId())) {
//            insert
            if (optionToppingDAO.existsOptionToppingByName(form.getName())){
                return Response.error(Constants.RESPONSE_CODE.EXISTS_OPTION);
            }
            optionTopping = new OptionTopping();
            optionTopping.setGuid(UUID.randomUUID().toString());
        } else {
            optionTopping = optionToppingDAO.findOptionToppingByName(form.getName());
        }
        optionTopping.setName(form.getName());
        optionTopping.setPrice(form.getPrice());
        optionToppingDAO.save(optionTopping);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }
    @DeleteMapping("delete/{id}")
    public @ResponseBody Response processDelete(@PathVariable int id){
        if (!optionToppingDAO.existsOptionToppingById(id)){
            return Response.error(Constants.RESPONSE_CODE.RECORD_DELETED);
        }
        optionToppingDAO.deleteById(id);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

    @GetMapping("/find_all")
    public @ResponseBody
    Response findAll(){
        List<OptionToppingBean> lst = optionToppingService.findOptionsTopping();
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(lst);
    }
}
