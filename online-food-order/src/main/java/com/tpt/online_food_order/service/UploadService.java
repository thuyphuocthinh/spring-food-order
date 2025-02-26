package com.tpt.online_food_order.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {
    public Map upload(MultipartFile file) throws Exception;
}
