package com.example.foodservice.controls;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.data.entity.Category;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.entity.Product;
import com.example.foodservice.data.repository.CategoriesRepository;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.data.repository.ProductRepository;
import com.example.foodservice.data.service.CategoryService;
import com.example.foodservice.data.service.ImageService;
import com.example.foodservice.data.service.ProductService;
import com.example.foodservice.domain.DataTableResults;
import com.example.foodservice.ultis.bean.CategoryBean;
import com.example.foodservice.ultis.form.CategoryForm;
import com.sun.org.apache.regexp.internal.RE;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@RestController
@RequestMapping("/categories")
public class CategoryControl {
    private static final Logger log = LoggerFactory.getLogger(CategoryControl.class);

    @Autowired
    private CategoriesRepository categoriesDAO;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @PostMapping
    public @ResponseBody
    Response saveOrUpdate(CategoryForm form) {
        Category category;
        ModelMapper modelMapper = new ModelMapper();
        if (categoriesDAO.existsCategoriesByName(form.getName())){
            return Response.warning(Constants.RESPONSE_CODE.EXISTS_NAME);
        }
        if (!CommonUtil.isEmpty(form.getId())) {
            // Update
            category = categoriesDAO.findById(form.getId()).orElse(null);
            if (CommonUtil.isEmpty(category)) {
                return Response.warning(Constants.RESPONSE_CODE.RECORD_DELETED);
            } else {
                category.setGuid(form.getGuid());
//                category.setCurrent(form.getCurrent());
            }
        } else {
            // insert
            UUID uuid = UUID.randomUUID();
            category = modelMapper.map(form, Category.class);
            category.setGuid(uuid.toString());
            category.setCurrent(true);
            category.setCreatedDate(new Date());
        }
        if (!CommonUtil.isEmpty(form.getMultipartFile())) {
            boolean uploadImage = imageService.uploadImageForCategory(category.getGuid(), form.getMultipartFile());
            if (!uploadImage) {
                return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.MESSAGE.UPLOAD_ERROR);
            }
        }
        category.setName(form.getName());
        category.setDescription(form.getDescription());
        categoriesDAO.save(category);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

//    @DeleteMapping("delete/{id}")
//    public @ResponseBody
//    Response deleteCategory(@PathVariable int id) throws ExecutionException, InterruptedException {
//        Category category = categoriesDAO.findById(id).orElse(null);
//        if (CommonUtil.isEmpty(category)) {
//            return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
//        }
//
//        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
//    }

    @GetMapping("/{id}")
    public @ResponseBody
    Response findById(@PathVariable int id) {
        Category category = categoriesDAO.findById(id).orElse(null);
        if (CommonUtil.isEmpty(category)) {
            return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
        } else {
            return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(category);
        }
    }

    @PostMapping("/delete")
    public @ResponseBody
    Response deleteCategories(CategoryForm form) throws ExecutionException, InterruptedException {
        List<Category> lst = new ArrayList<>();
        List<Integer> dataId = form.getCategories();
        for (int i = 0; i < dataId.size(); i++) {
            Category category = categoriesDAO.findById(dataId.get(i)).orElse(null);
            if (CommonUtil.isEmpty(category)) {
                return Response.warning(Constants.RESPONSE_CODE.WARNING,
                        Constants.RESPONSE_CODE.RECORD_DELETED);
            }
            lst.add(category);
        }
        processDeleteCategories(lst);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

    @GetMapping("/search")
    public @ResponseBody
    DataTableResults<CategoryBean> processSearch(CategoryForm form) {
        return categoryService.getDataTables(form);
    }

    @GetMapping("/find_all")
    public @ResponseBody
    Response findAll() {
        List<Category> categories = (List<Category>) categoriesDAO.findAll();
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(categories);
    }

    private void processDeleteCategories(List<Category> categories) throws ExecutionException, InterruptedException {
        for (Category item : categories) {
            CompletableFuture<Void> threadDelImage = CompletableFuture.runAsync(() -> {
                log.info("Find Image Of Category");
                Image imgCategory = imageRepository.findByGuidCategory(item.getGuid())
                        .orElse(null);
                if (!CommonUtil.isEmpty(imgCategory)) {
                    imageService.delete(imgCategory.getImageId());
                }
            });
            CompletableFuture<Void> threadDelProduct = CompletableFuture.runAsync(() -> {
                log.info("Find Image Of Category");
                List<Product> lstProduct = productRepository.findAllByGuidCategory(item.getGuid());
                productService.delLstProduct(lstProduct);
            });
            threadDelImage.get();
            threadDelProduct.get();
            categoriesDAO.deleteById(item.getId());
        }
    }
}
