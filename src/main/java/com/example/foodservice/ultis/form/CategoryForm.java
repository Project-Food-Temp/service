package com.example.foodservice.ultis.form;

import com.example.foodservice.data.entity.Category;
import com.example.foodservice.ultis.bean.CategoryBean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForm {
    private Integer id;
    private String guid;
    private String name;
    private String description;
    private Boolean current;
    private String urlImg;
//    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private MultipartFile multipartFile;
    private List<Integer> categories;
}
