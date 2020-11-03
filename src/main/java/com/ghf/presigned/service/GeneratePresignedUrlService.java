package com.ghf.presigned.service;

import com.ghf.presigned.model.FileUploadedAsAnObjectViaPresignedUrl;

public interface GeneratePresignedUrlService {
    public String generatePresignedUrl(FileUploadedAsAnObjectViaPresignedUrl contentUploadedAsAnObjectViaPresignedUrl);
}
