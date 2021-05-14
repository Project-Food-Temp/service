package com.example.foodservice.control;

import com.example.foodservice.constants.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.data.entity.Category;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.repository.CategoriesRepository;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.data.service.CategoryService;
import com.example.foodservice.data.service.ImageService;
import com.example.foodservice.service.CloudinaryService;
import com.example.foodservice.ultis.bean.ImageBean;
import com.example.foodservice.ultis.form.CategoryForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@RestController
@RequestMapping("/categories")
public class CategoryControl {
    @Autowired
    private CategoriesRepository categoriesDAO;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/add")
    public @ResponseBody
    Response saveOrUpdate( CategoryForm form) {
        Category category;
        Image image;
        ModelMapper modelMapper = new ModelMapper();
        if (form.getId() > 0) {
            // Update
            category = categoriesDAO.findById(form.getId()).orElse(null);
            if (CommonUtil.isEmpty(category)) {
                return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
            } else {
                category = modelMapper.map(form, Category.class);
            }
        } else {
            // insert
            UUID uuid = UUID.randomUUID();
            category = modelMapper.map(form, Category.class);
            category.setGuid(uuid.toString());
            category.setCurrent(true);
            category.setCreatedDate(new Date());
        }
        if (!CommonUtil.isEmpty(form.getMultipartFile())){
            boolean uploadImage = imageService.uploadImageForCategory(category.getGuid(), form.getMultipartFile());
            if (!uploadImage){
                return Response.warning(Constants.RESPONSE_CODE.WARNING,Constants.MESSAGE.UPLOAD_ERROR);
            }
        }
        categoriesDAO.save(category);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    Response deleteCategory(@PathVariable int id) {
        Category category = categoriesDAO.findById(id).orElse(null);
        if (CommonUtil.isEmpty(category)) {
            return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
        } else {
            categoriesDAO.deleteById(id);
            return Response.success(Constants.RESPONSE_CODE.SUCCESS);
        }
    }

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

    @GetMapping("/list")
    public @ResponseBody Response getList(){
        List<Category> categories = (List<Category>) categoriesDAO.findAll();
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(categories);
    }

//    @GetMapping("/list/{id}")
//    private @ResponseBody Response getListCategoryById(@PathVariable int id){
//
//    }

//    @GetMapping("/search")
//    public @ResponseBody
//    Response processSearch(CategoryForm form) {
//
//    }
}
