package com.example.foodservice.data.service;

import com.example.foodservice.common.VfData;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.entity.Product;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.data.repository.ProductRepository;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.CategoryBean;
import com.example.foodservice.ultis.bean.ProductBean;
import com.example.foodservice.ultis.form.CategoryForm;
import com.example.foodservice.ultis.form.ProductForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Service
@Transactional
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VfData vfData;


    public void delLstProduct(List<Product> lstProduct) {
        lstProduct.forEach(this::delProduct);
    }

    public void delProduct(Product product) {
        List<Image> lstImgProduct = imageRepository.findByGuidProduct(product.getGuid());
        lstImgProduct.forEach(img -> {
            imageService.delete(img.getImageId());
        });
        productRepository.delete(product);
    }

    public DataTableResults<ProductBean> getDataTables(ProductForm form) {
        DataTableResults<ProductBean> dtTable = productRepository.getDatatable(vfData,form);
        return dtTable;
    }
}
