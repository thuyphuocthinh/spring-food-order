package com.tpt.online_food_order.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public String upload(MultipartFile file) throws Exception;
}
