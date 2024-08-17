package com.mithi.animaldemo.service;

import com.mithi.animaldemo.model.Image;
import com.mithi.animaldemo.model.ImageDetails;
import com.mithi.animaldemo.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    private ImageRepository imageRepository;

    public boolean saveImage(ImageDetails imagedetails) {
        log.info("Saving image " + imagedetails.name() + " byte size : " + imagedetails.image().length);
        try {
            Image image = new Image();
            image.setName(imagedetails.name());
            image.setContent(imagedetails.image());

            Image imageSaved = imageRepository.save(image);
            log.info("Image saved successfully {}", imageSaved);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Optional<Image> getImage(String id) {
        return imageRepository.findById(id);
    }
}
