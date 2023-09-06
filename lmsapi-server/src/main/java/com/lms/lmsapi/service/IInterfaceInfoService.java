package com.lms.lmsapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.lmsapi.entity.dto.interfaceInfo.AddInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.InvokeInterfaceDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.PageInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.UpdateInterfaceInfoDto;
import com.lms.lmsapi.entity.vo.interfaceInfo.InterfaceInfoVo;
import com.lms.lmscommon.model.entity.InterfaceInfo;

import java.util.List;


/**
 *
 */
public interface IInterfaceInfoService extends IService<InterfaceInfo> {



     Boolean saveInterface(AddInterfaceInfoDto addInterfaceInfoDto,Long uid);

     Boolean updateInterface(UpdateInterfaceInfoDto updateInterfaceInfoDto);

     Boolean deleteInterface(List<Long> ids);

     Page<InterfaceInfoVo>  pageInterfaceVo(PageInterfaceInfoDto pageInterfaceInfoDto);

     InterfaceInfoVo getInterfaceById(Long id);

     Boolean onlineInterface(Long id);
     Boolean offlineInterface(Long id);



     String invokeinterface(InvokeInterfaceDto invokeInterfaceDto,String accessKey,String secretKey);

}
