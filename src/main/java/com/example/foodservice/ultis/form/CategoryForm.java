package com.example.foodservice.ultis.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

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
    private int id;
    private String guid;
    private String name;
    private String description;
    private boolean isCurrent;
    private Date createdDate;
    @JsonDeserialize
    private MultipartFile multipartFile;
}
