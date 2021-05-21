package com.example.foodservice.controls;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.entity.Product;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.data.repository.ProductRepository;
import com.example.foodservice.data.service.ImageService;
import com.example.foodservice.ultis.form.ProductForm;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nhan Nguyen on 5/16/2021
 *
 * @author Nhan Nguyen
 * @date 5/16/2021
 */

@RestController
@RequestMapping("/product")
public class ProductControl {
    private static final Logger log = LoggerFactory.getLogger(ProductControl.class);
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    @PostMapping()
    public @ResponseBody
    Response saveOrUpdate(ProductForm form) {
        Product product;
        ModelMapper modelMapper = new ModelMapper();
        if (form.getId() > 0) {
            //Update
            product = productRepository.findById(form.getId()).orElse(null);
            if (CommonUtil.isEmpty(product)) {
                return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
            } else {
                product = modelMapper.map(form, Product.class);
            }
        } else {
//            insert
            UUID uuid = UUID.randomUUID();
            product = modelMapper.map(form, Product.class);
            product.setCreatedDate(new Date());
            product.setGuid(uuid.toString());
            product.setCurrent(true);
            product.setGuidCategory(form.getGuidCategory());
        }
        if ((!CommonUtil.isEmpty(form.getFileImages()))) {
            boolean flagUplaod = imageService.uploadFileForProduct(product.getGuid(), form.getFileImages());
            if (!flagUplaod) {
                return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.MESSAGE.UPLOAD_ERROR);
            }
        }
        productRepository.save(product);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

    @DeleteMapping("delete/{id}")
    public @ResponseBody Response deleteByIdProduct(@PathVariable int id){
        Product product = productRepository.findById(id).orElse(null);
        if (CommonUtil.isEmpty(product)){
            return Response.warning(Constants.RESPONSE_CODE.RECORD_DELETED);
        }
        List<Image> lstImageByProduct = imageRepository.findByGuidProduct(product.getGuid());
        if (!CommonUtil.isNullOrEmpty(lstImageByProduct)){
            lstImageByProduct.forEach(image -> {
                imageService.delete(image.getImageId());
            });
        }
        productRepository.deleteById(id);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }
}
