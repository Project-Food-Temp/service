package com.example.foodservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NhanNguyen on 5/10/2021
 *
 * @author: NhanNguyen
 * @date: 5/10/2021
 */
@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);
    Cloudinary cloudinary;
    private Map<String,String> valuesMap = new HashMap<>();

    public CloudinaryService() {
        valuesMap.put("cloud_name","djugby2md");
        valuesMap.put("api_key","585188152353576");
        valuesMap.put("api_secret","7cBbkK1mCetTs72b_Q8X6EzhkwY");
//        valuesMap.put("folder","demo");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile){

        File file = convert(multipartFile);
        Map result = null;
        try {
            result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        file.delete();
        return result;
    }
    public Map delete(String id) {
        Map reslut = null;
        try {
            reslut = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
        return reslut;
    }

    private File convert(MultipartFile multipartFile){
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return file;
    }
}
