package com.lms.lmsapi.controller;

import com.lms.lmsapi.service.IDemoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
public class DemoController {


    //上传图片接口

    @Resource
    private IDemoService demoService;
    @PostMapping("/uploadImg")
    public String uploadImg(@RequestBody MultipartFile file){
       return  demoService.uploadImage(file);
    }


    @GetMapping("/test")
    public String testConnection(){
        return "演示访问接口";
    }

}
