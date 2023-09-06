package com.lms.lmsapi;

import com.lms.lmsapi.client.LmsApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainApplication.class)
public class lmsapiTest {

    @Test
    public void test(){
        LmsApiClient lmsApiClient=new LmsApiClient("yupi","abcdefg");
        System.out.println(lmsApiClient.testConnection());
    }
}
