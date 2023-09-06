package com.lms.lmsapi.entity.vo.interfaceInfo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InterfaceInfoVo implements Serializable {


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

    private Integer status;

    /*
    接口请求方法  GET POST PUT DELETE
     */
    private String method;

    /*
       创建者
     */
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
