package com.ghf.presigned.model;

import java.io.Serializable;

public class FileUploadedAsAnObjectViaPresignedUrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ec2Path;
    private String fileName;

    public String getEc2Path() {
        return ec2Path;
    }

    public void setEc2Path(String ec2Path) {
        this.ec2Path = ec2Path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
