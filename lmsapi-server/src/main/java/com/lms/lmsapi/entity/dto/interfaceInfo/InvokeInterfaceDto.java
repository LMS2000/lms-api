package com.lms.lmsapi.entity.dto.interfaceInfo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;
import java.io.Serializable;
@Data
public class InvokeInterfaceDto implements Serializable {

    @Positive(message = "id不合法")
    private Long id;
    @Length(max = 255)
    private String requestParams;
}
