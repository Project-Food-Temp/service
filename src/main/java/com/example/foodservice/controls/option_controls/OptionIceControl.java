package com.example.foodservice.controls.option_controls;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.controls.CategoryControl;
import com.example.foodservice.data.entity.OptionIce;
import com.example.foodservice.data.repository.OptionIceRepository;
import com.example.foodservice.data.service.OptionIceService;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.OptionIceBean;
import com.example.foodservice.ultis.form.OptionIceForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nhan Nguyen on 5/27/2021
 *
 * @author Nhan Nguyen
 * @date 5/27/2021
 */
@RestController
@RequestMapping("/options/ice")
public class OptionIceControl {
    private static final Logger log = LoggerFactory.getLogger(OptionIceControl.class);

    @Autowired
    private OptionIceRepository optionIceDAO;

    @Autowired
    private OptionIceService optionIceService;


    @PostMapping
    public @ResponseBody
    Response saveOption(OptionIceForm form){
        if (!CommonUtil.isEmpty(optionIceDAO.findOptionIceByOptionName(form.getOptions()))){
            return Response.error(Constants.RESPONSE_CODE.EXISTS_OPTION);
        }
        OptionIce optionIce = new OptionIce();
        UUID uuid = UUID.randomUUID();
        optionIce.setGuid(uuid.toString());
        optionIce.setOptionName(form.getOptions());
        optionIceDAO.save(optionIce);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

    @GetMapping("/find_all")
    public @ResponseBody
    Response findAll(){
        List<OptionIceBean> lst = optionIceService.findAllOptionIce();
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(lst);
    }
    @DeleteMapping("delete/{id}")
    public @ResponseBody
    Response deleteCategory(@PathVariable int id) throws ExecutionException, InterruptedException {
        OptionIce optionIce = optionIceDAO.findById(id).orElse(null);
        if (CommonUtil.isEmpty(optionIce)) {
            return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
        }
        optionIceDAO.deleteById(id);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }
}
