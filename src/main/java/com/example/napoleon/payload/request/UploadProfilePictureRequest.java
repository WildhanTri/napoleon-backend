package com.example.napoleon.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadProfilePictureRequest {
	private MultipartFile picture;

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
