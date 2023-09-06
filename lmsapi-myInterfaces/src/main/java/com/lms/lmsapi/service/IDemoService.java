package com.lms.lmsapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface IDemoService {


    String uploadImage(MultipartFile file);
}
