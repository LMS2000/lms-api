package com.lms.lmsapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.lmsapi.entity.dto.interfaceInfo.PageInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.userInterfaceInfo.PageUserInterfaceInfoDto;
import com.lms.lmsapi.entity.vo.userInterfaceInfo.UserInterfaceInfoVo;
import com.lms.lmscommon.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface IUserInterfaceInfoService extends IService<UserInterfaceInfo> {
//    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);
//
//    /**
//     * 调用接口统计
//     * @param interfaceInfoId
//     * @param userId
//     * @return
//     */
//    boolean invokeCount(long interfaceInfoId, long userId);


    Boolean saveUserInterface(Long uid,Long interfaceId);

    Page<UserInterfaceInfoVo> pageUseInterfaceInfo(PageUserInterfaceInfoDto pageInterfaceInfoDto);

    Page<UserInterfaceInfoVo> pageCurrentInterfaceInfo(Long uid,PageUserInterfaceInfoDto pageInterfaceInfoDto);
}
