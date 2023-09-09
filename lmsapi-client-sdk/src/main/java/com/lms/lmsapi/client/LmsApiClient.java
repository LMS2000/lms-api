package com.lms.lmsapi.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.MultipartOutputStream;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.lms.lmsapi.utils.SignUtils.genSign;


/**
 * 调用第三方接口的客户端
 *
 * @author LMS
 */
public class LmsApiClient {

    private static final String GATEWAY_HOST = "http://localhost:8090";

    private String accessKey;
    private String secretKey;

    public LmsApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    public String uploadImage(MultipartFile file) throws IOException {

        HttpResponse response = HttpUtil.createPost(GATEWAY_HOST+"/myapi/demo/uploadImg")
                .header("Content-Type", "multipart/form-data")
                .header("Accept", "application/json")
                .addHeaders(getHeaderMap("avatar"))
                .body(file.getBytes())
                .execute();
        String responseBody = response.body();
        return responseBody;
    }

   public String testConnection(){
       HttpResponse response = HttpUtil.createGet(GATEWAY_HOST + "/myapi/demo/test")
               .addHeaders(getHeaderMap("test")).execute();
        return response.body();
   }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 一定不能直接发送
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }


}
