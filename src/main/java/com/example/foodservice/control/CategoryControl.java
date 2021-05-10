package com.example.foodservice.control;

import com.example.foodservice.constants.CommonUtil;
import com.example.foodservice.constants.Constants;
import com.example.foodservice.constants.Response;
import com.example.foodservice.data.entity.Category;
import com.example.foodservice.data.repository.CategoriesRepository;
import com.example.foodservice.data.service.CategoryService;
import com.example.foodservice.ultis.form.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/add")
    public @ResponseBody
    Response saveOrUpdate(@RequestBody CategoryForm form) {
        Category category;
        if (form.getId() > 0) {
            category = categoriesDAO.findById(form.getId()).orElse(null);
            if (CommonUtil.isEmpty(category)) {
                return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.RESPONSE_CODE.RECORD_DELETED);
            } else {
                category.setCurrent(form.isCurrent());
            }
        } else {
            category = new Category();
            category.setCurrent(true);
            category.setCreatedDate(new Date());
        }
        category.setId(form.getId());
        category.setDescription(form.getDescription());
        category.setName(form.getName());
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(category);
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
