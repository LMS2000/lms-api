package com.lms.lmsapi.entity.dto.interfaceInfo;

import com.lms.page.CustomPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageInterfaceInfoDto  extends CustomPage implements Serializable {

    private Integer method;

    private Integer status;

    private String name;

}
