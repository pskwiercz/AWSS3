package com.pskwiercz.controller;

import com.pskwiercz.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class S3Controller {

    private final S3Service service;

    @Autowired
    public S3Controller(S3Service s3service) {
        this.service = s3service;
    }

    // curl 'http://localhost:9030/api/upload?key=some-key-name&file=some-file'

    @PutMapping(value = "/upload")
    public String upload(@RequestParam(name = "key") String key, @RequestParam("file") MultipartFile file)
            throws Exception {
        if (file.isEmpty()) {
            return "nothing to upload";
        }

        return service.uploadObject(key, file);
    }
}
