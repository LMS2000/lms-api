package com.lms.lmsapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lms.lmsapi.constants.CommonConstants;
import com.lms.lmsapi.exception.BusinessException;
import com.lms.lmsapi.service.IInterfaceInfoService;
import com.lms.lmscommon.model.entity.InterfaceInfo;
import com.lms.lmscommon.model.enums.MethodEnum;
import com.lms.lmscommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private IInterfaceInfoService interfaceInfoService;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, Integer method) {
       BusinessException.throwIf(StringUtils.isEmpty(url));
       BusinessException.throwIfNot(validMethod(method));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        queryWrapper.eq("delete_flag", CommonConstants.NOT_DELETED).eq("status",1);
        return interfaceInfoService.getOne(queryWrapper);
    }
    private boolean validMethod(Integer method){
        List<Integer> values = MethodEnum.getValues();
        return !ObjectUtils.isEmpty(method)&&values.contains(method);
    }
}
