package com.lms.lmsapi.entity.dto.interfaceInfo;

import com.lms.lmsapi.valid.RangeCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class AddInterfaceInfoDto implements Serializable {

    /*
     接口名
      */
    @NotNull(message = "接口名不能为空")
    @NotBlank(message = "接口名不能为空")
    private String name;


    /*
    接口描述
     */
    private String description;

    /*
      接口地址
     */
    @NotNull(message = "接口地址不能为空")
    @NotBlank(message = "接口地址不能为空")
    private String url;

    /*
    接口请求参数
     */
    @NotNull(message = "接口地址不能为空")
    @NotBlank(message = "接口地址不能为空")
    private String requestParams;

    /*
     接口请求头
     */
    @NotNull(message = "接口地址不能为空")
    @NotBlank(message = "接口地址不能为空")
    private String requestHeader;

    /*
    接口响应头
     */
    @NotNull(message = "接口地址不能为空")
    @NotBlank(message = "接口地址不能为空")
    private String responseHeader;

    /*
    接口状态， 0为下线  1为上线
     */
    @NotNull
    @RangeCheck(range = {0,1})
    private Integer status;

    /*
    接口请求方法  GET POST PUT DELETE
     */
    @NotNull
    private Integer method;




}
