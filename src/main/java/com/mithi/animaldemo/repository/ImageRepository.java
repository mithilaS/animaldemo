package com.mithi.animaldemo.repository;

import com.mithi.animaldemo.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}
