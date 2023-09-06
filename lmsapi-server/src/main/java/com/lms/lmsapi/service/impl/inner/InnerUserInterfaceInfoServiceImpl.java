package com.lms.lmsapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lms.lmsapi.exception.BusinessException;
import com.lms.lmsapi.service.IUserInterfaceInfoService;
import com.lms.lmscommon.model.entity.UserInterfaceInfo;
import com.lms.lmscommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {



    @Resource
    private IUserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {

        // 判断
    BusinessException.throwIf(interfaceInfoId<0||userId<0);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interface_info_id", interfaceInfoId);
        updateWrapper.eq("user_id", userId);

//        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("left_num = left_num - 1, total_num = total_num + 1");
        return userInterfaceInfoService.update(updateWrapper);
    }

    @Override
    public UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long uid) {
        return userInterfaceInfoService.getOne(new QueryWrapper<UserInterfaceInfo>()
                .eq("user_id",uid).eq("interface_info_id",interfaceInfoId).eq("status",0));
    }
}
