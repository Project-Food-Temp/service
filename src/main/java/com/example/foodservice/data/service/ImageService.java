package com.example.foodservice.data.service;

import com.example.foodservice.common.CommonUtil;
import com.example.foodservice.data.entity.Image;
import com.example.foodservice.data.repository.ImageRepository;
import com.example.foodservice.service.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by NhanNguyen on 5/5/2021
 *
 * @author: NhanNguyen
 * @date: 5/5/2021
 */
@Service
@Transactional
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    /**
     *
     * @param guid
     * @param multipartFile
     * @return
     */
    @Transactional
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
                delete(image.getImageId());
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

    /**
     *
     * @param guid
     * @param fileImages
     * @return
     */
    @Transactional
    public boolean uploadFileForProduct(String guid, MultipartFile[] fileImages) {
        List<Image> lstImage = imageRepository.findByGuidProduct(guid);
        if (!CommonUtil.isNullOrEmpty(lstImage)) {
            lstImage.forEach(item -> {
               delete(item.getImageId());
            });
            lstImage.clear();
        }
        for (int i = 0; i < fileImages.length; i++) {
            Image image = new Image();
            try {
                BufferedImage bi = ImageIO.read(fileImages[i].getInputStream());
                if (CommonUtil.isEmpty(bi)) {
                    return false;
                }
                Map result = cloudinaryService.upload(fileImages[i]);
                image.setGuidProduct(guid);
                image.setName(String.valueOf(result.get("original_filename")));
                image.setImageUrl(String.valueOf(result.get("url")));
                image.setImageId(String.valueOf(result.get("public_id")));
                lstImage.add(image);
            } catch (IOException e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        imageRepository.saveAll(lstImage);
        return true;
    }

    public void delete(String imageId) {
        cloudinaryService.delete(imageId);
        imageRepository.deleteByImageId(imageId);
    }
}
