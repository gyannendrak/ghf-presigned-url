package com.ghf.presigned.service;

import com.ghf.presigned.model.FileUploadedAsAnObjectViaPresignedUrl;

import java.net.URL;

public interface GeneratePresignedUrlService {
    public String generatePresignedUrl(FileUploadedAsAnObjectViaPresignedUrl contentUploadedAsAnObjectViaPresignedUrl);
}
