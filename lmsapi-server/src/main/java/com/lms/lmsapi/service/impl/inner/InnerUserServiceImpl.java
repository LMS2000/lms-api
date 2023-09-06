package com.lms.lmsapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lms.lmsapi.constants.UserConstants;
import com.lms.lmsapi.service.IUserService;
import com.lms.lmscommon.model.entity.User;
import com.lms.lmscommon.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private IUserService userService;
    @Override
    public User getInvokeUser(String accessKey) {
        return userService.getOne(new QueryWrapper<User>().eq("access_key",accessKey).eq("enable", UserConstants.ENABLE)
                .eq("delete_flag",UserConstants.NOT_DELETED));
    }
}
