package com.lms.lmsapi.entity.dto.userInterfaceInfo;

import com.lms.page.CustomPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageUserInterfaceInfoDto extends CustomPage implements Serializable {

    private Integer method;

    private String name;

    private Integer status;
}
