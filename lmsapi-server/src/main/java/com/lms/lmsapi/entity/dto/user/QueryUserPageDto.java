package com.lms.lmsapi.entity.dto.user;


import com.lms.lmsapi.valid.RangeCheck;
import com.lms.page.CustomPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryUserPageDto extends CustomPage implements Serializable {




    private String username;

    @RangeCheck(range = {0,1})
    private Integer enable;
}
