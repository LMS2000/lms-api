package com.lms.lmscommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    private String username;

    private String nickname;
    private String password;

    private String email;

    private  Integer enable;

    private String avatar;


    private Integer deleteFlag;


    private  String userRole;

    private String accessKey;

    private String secretKey;


    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}