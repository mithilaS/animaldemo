package com.mithi.animaldemo.worker;

import com.mithi.animaldemo.model.ImageDetails;
import com.mithi.animaldemo.rest.AnimalRestClient;
import com.mithi.animaldemo.service.ImageService;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AnimalWorker {

    private static final Logger log = LoggerFactory.getLogger(AnimalWorker.class);
    byte[] animalImage;
    @Autowired
    private AnimalRestClient animalRestClient;

    @Autowired
    private ImageService imageService;

    @JobWorker(type = "fetch-animal")
    public Map fetchAnimal(
            @Variable String animalName) throws Exception {

        Map map = new HashMap();
        try {
            log.info("Fetching animal {}", animalName);
            byte[] animal;
            String name;

            if (animalName.equals("dogs")) {
                animalImage = animalRestClient.getDogDetails();
            } else if (animalName.equals("cats")) {
                animalImage = animalRestClient.getCatDetails();
            } else if (animalName.equals("bears")) {
                animalImage = animalRestClient.getBearDetails();
            }

            map.put("name", animalName);
            map.put("image", animalImage);

        } catch (Exception e) {
            log.error(e.getMessage());
            map.put("fetchStatus", "failure");
        }
        return map;
    }

    @JobWorker(type = "save-animal")
    public Map saveAnimalDetails(
            @VariablesAsType ImageDetails imageDetails) throws Exception {
        boolean saved = false;
        try {
            log.info("In saveAnimalDetails method {}", imageDetails);
            if (imageDetails != null && imageDetails.image() != null) {
                log.info("Saving animal details {}", imageDetails.name() + "content length  {} ", imageDetails.image().length);
                saved = imageService.saveImage(imageDetails);
            }
            if (saved) {
                return Map.of("saveStatus", "Completed successfully");
            } else {
                return Map.of("saveStatus", "Failed saving animal details");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return Map.of("saveStatus", e.getMessage());
        }
    }

}
