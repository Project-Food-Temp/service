package com.example.foodservice.data.service;

import com.example.foodservice.constants.CommonUtil;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.service.CloudinaryService;
import com.example.foodservice.ultis.bean.ImageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public ImageBean uploadImage(MultipartFile multipartFile) {
        ImageBean image = new ImageBean();
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (Objects.isNull(bi)) {
                return null;
            }
            Map result = cloudinaryService.upload(multipartFile);
            image.setName(String.valueOf(result.get("original_filename")));
            image.setImageUrl(String.valueOf(result.get("url")));
            image.setImageId(String.valueOf(result.get("public_id")));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
        return image;
    }

    public boolean uploadImageForCategory(String guid, MultipartFile multipartFile) {
        Image image;
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (Objects.isNull(bi)) {
                return false;
            }
            Map result = cloudinaryService.upload(multipartFile);
            image = imageRepository.findByGuidCategory(guid).orElse(null);
            if (Objects.isNull(image)) {
                image = new Image();
                image.setGuidCategory(guid);
            } else {
                cloudinaryService.delete(image.getImageId());
            }
            image.setName(String.valueOf(result.get("original_filename")));
            image.setImageUrl(String.valueOf(result.get("url")));
            image.setImageId(String.valueOf(result.get("public_id")));
            imageRepository.save(image);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(String imageId) {
        cloudinaryService.delete(imageId);
        imageRepository.deleteByImageId(imageId);
        return true;
    }
}
