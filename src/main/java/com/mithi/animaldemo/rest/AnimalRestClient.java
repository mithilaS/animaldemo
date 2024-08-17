package com.mithi.animaldemo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnimalRestClient {

    private static final Logger log = LoggerFactory.getLogger(AnimalRestClient.class);
    @Autowired
    private RestTemplate restTemplate;

    public byte[] getDogDetails() {
        log.info("In getDogDetails rest call");
        byte[] image = restTemplate.getForObject("https://place.dog/300/200", byte[].class);
        if (image != null)
            log.info("Image length of woof woof : {}", String.valueOf(image.length));
        return image;
    }

    public byte[] getCatDetails() {
        log.info("In getCatDetails rest call");
        byte[] image = restTemplate.getForObject("https://place.dog/300/200", byte[].class);
        if (image != null)
            log.info("Image length of meow meow : {}", String.valueOf(image.length));
        return image;
    }

    public byte[] getBearDetails() {
        log.info("In getBearDetails rest call");
        byte[] image = restTemplate.getForObject("https://placebear.com/200/300", byte[].class);
        if (image != null)
            log.info("Image length of growl growl : {}", String.valueOf(image.length));
        return image;
    }

}
