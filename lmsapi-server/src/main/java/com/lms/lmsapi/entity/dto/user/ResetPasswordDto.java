package com.lms.lmsapi.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class ResetPasswordDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String oldPassword;

    private String newPassword;

    private String emailCode;

}
