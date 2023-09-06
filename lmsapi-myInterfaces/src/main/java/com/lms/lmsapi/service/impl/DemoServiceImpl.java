package com.lms.lmsapi.service.impl;

import cn.hutool.core.io.FileUtil;
import com.lms.contants.HttpCode;
import com.lms.lmsapi.client.OssClient;
import com.lms.lmsapi.config.OssProperties;
import com.lms.lmsapi.enums.DateTimePatternEnum;
import com.lms.lmsapi.exception.BusinessException;
import com.lms.lmsapi.service.IDemoService;
import com.lms.lmsapi.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Service
public class DemoServiceImpl implements IDemoService {



    @Resource
    private OssProperties ossProperties;

    @Resource
    private OssClient ossClient;
    @Override
    public String uploadImage(MultipartFile file) {
        //校验文件
        validFile(file);
        String bucketName =    DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
        //上传文件
        String filePath;
        try {
            filePath =
                    com.lms.lmsapi.utils.FileUtil.generatorFileName(file.getOriginalFilename() == null ? file.getName() : file.getOriginalFilename());
            ossClient.putObject(bucketName, filePath, file.getInputStream());
        } catch (IOException e) {
            throw new BusinessException(HttpCode.OPERATION_ERROR, "上传图片失败");
        }
        String fileUrl = com.lms.lmsapi.utils.FileUtil.getFileUrl(ossProperties.getEndpoint(), "static", bucketName, filePath);
        return fileUrl;
    }

    private void validFile(MultipartFile file) {
        // 文件大小
        long fileSize = file.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(file.getOriginalFilename());
        final long ONE_M = 1024 * 1024 * 10L;
        if (fileSize > ONE_M) {
            throw new BusinessException(HttpCode.PARAMS_ERROR, "文件大小不能超过 10M");
        }
        if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
            throw new BusinessException(HttpCode.PARAMS_ERROR, "文件类型错误");
        }
    }
}
