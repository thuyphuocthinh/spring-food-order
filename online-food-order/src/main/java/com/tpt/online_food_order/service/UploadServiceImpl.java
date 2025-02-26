package com.tpt.online_food_order.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {

    private final Cloudinary cloudinary;

    public UploadServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map upload(MultipartFile file) throws Exception {
        try{
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }
}
