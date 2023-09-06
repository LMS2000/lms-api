package com.lms.lmsapi.entity.dto.interfaceInfo;

import com.lms.lmsapi.valid.RangeCheck;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
@Data
public class UpdateInterfaceInfoDto implements Serializable {


    @NotNull
    @Positive(message = "id不合法")
    private Long id;
    /*
    接口名
     */
    private String name;


    /*
    接口描述
     */
    private String description;

    /*
      接口地址
     */
    private String url;

    /*
    接口请求参数
     */
    private String requestParams;

    /*
     接口请求头
     */
    private String requestHeader;

    /*
    接口响应头
     */
    private String responseHeader;

    /*
    接口状态， 0为下线  1为上线
     */

    @RangeCheck(range = {0,1})
    private Integer status;

    /*
    接口请求方法  GET POST PUT DELETE
     */

    private Integer method;
}
