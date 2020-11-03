package com.ghf.presigned.controller;

import com.ghf.presigned.model.FileUploadedAsAnObjectViaPresignedUrl;
import com.ghf.presigned.schema.GHFApiResponse;
import com.ghf.presigned.service.GeneratePresignedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PresignedUrlController {

    @Autowired
    private GeneratePresignedUrlService generatePresignedUrlService;

    @PostMapping(value = "/presignedurl")
    public GHFApiResponse<String> process(@RequestBody FileUploadedAsAnObjectViaPresignedUrl fileUploadedAsAnObjectViaPresignedUrl) {
        String url = generatePresignedUrlService.generatePresignedUrl(fileUploadedAsAnObjectViaPresignedUrl);
        return new GHFApiResponse<>(url, HttpStatus.OK.value());
    }
}
