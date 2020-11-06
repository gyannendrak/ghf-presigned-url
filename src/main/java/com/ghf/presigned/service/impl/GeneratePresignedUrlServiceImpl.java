package com.ghf.presigned.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.ghf.presigned.model.FileUploadedAsAnObjectViaPresignedUrl;
import com.ghf.presigned.service.GeneratePresignedUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;


@Service
public class GeneratePresignedUrlServiceImpl implements GeneratePresignedUrlService {

    @Value("${aws.access.key.id}")
    private String AWS_ACCESS_KEY;
    @Value("${aws.secret.access.key}")
    private String AWS_SECRET_KEY;
    @Value("${aws.s3.bucket.name}")
    private String AWS_BUCKET_NAME;
    @Value("${aws.s3.client.region}")
    private String CLIENT_REGION;


    @Override
    public String generatePresignedUrl(FileUploadedAsAnObjectViaPresignedUrl fileUploadedAsAnObjectViaPresignedUrl) {
        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY,AWS_SECRET_KEY);
        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(CLIENT_REGION).build();

        String ec2Path = fileUploadedAsAnObjectViaPresignedUrl.getEc2Path();
        String fileName = fileUploadedAsAnObjectViaPresignedUrl.getFileName();

        try {
            uploadObject(s3Client, AWS_BUCKET_NAME, ec2Path, ec2Path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = getSignedUrlForS3File(s3Client, AWS_BUCKET_NAME, ec2Path);
        System.out.println("Received response:"+url);
        return url.toString();
    }

    public static String getSignedUrlForS3File(AmazonS3 s3Client, String s3BucketName, String fileName){
        Date expirationTime = getExpirationTime();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(s3BucketName, fileName, HttpMethod.GET);
        request.setExpiration(expirationTime);
        String url = s3Client.generatePresignedUrl( request ).toString();
        System.out.println("Pre-Signed URL = " + url);
        return url;
    }

    public static void uploadObject(AmazonS3 s3Client, String s3BucketName, String folderPathFileName, String fileName) throws AmazonServiceException, AmazonClientException, InterruptedException{
        TransferManager tm = new TransferManager(s3Client);
        PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, folderPathFileName, new File(fileName));
        Upload myUpload = tm.upload( putObjectRequest );
        myUpload.waitForCompletion();
        tm.shutdownNow();
    }

    public static Date getExpirationTime(){
        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
