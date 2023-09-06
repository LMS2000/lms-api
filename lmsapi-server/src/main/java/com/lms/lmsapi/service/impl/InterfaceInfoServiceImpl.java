package com.lms.lmsapi.service.impl;

import com.amazonaws.services.s3.model.BucketAccelerateStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.lmsapi.client.LmsApiClient;
import com.lms.lmsapi.constants.CommonConstants;
import com.lms.lmsapi.entity.dto.interfaceInfo.AddInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.InvokeInterfaceDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.PageInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.UpdateInterfaceInfoDto;
import com.lms.lmsapi.entity.vo.interfaceInfo.InterfaceInfoVo;
import com.lms.lmsapi.exception.BusinessException;
import com.lms.lmsapi.mapper.InterfaceInfoMapper;
import com.lms.lmsapi.service.IInterfaceInfoService;
import com.lms.lmsapi.utils.MybatisUtils;
import com.lms.lmscommon.model.entity.InterfaceInfo;
import com.lms.lmscommon.model.enums.InterfaceInfoStatusEnum;
import com.lms.lmscommon.model.enums.MethodEnum;
import io.lettuce.core.StrAlgoArgs;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static com.lms.lmsapi.entity.factory.InterfaceFactory.INTERFACE_CONVERTER;

@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements IInterfaceInfoService {





    @Override
    public Boolean saveInterface(AddInterfaceInfoDto addInterfaceInfoDto,Long uid) {

        //校验接口名是否重复
        String name = addInterfaceInfoDto.getName();
        Integer method = addInterfaceInfoDto.getMethod();
        Integer status = addInterfaceInfoDto.getStatus();
        BusinessException.throwIf(MybatisUtils.existCheck(this, Map.of("name",name)));
        BusinessException.throwIfNot(validMethod(method));
        BusinessException.throwIfNot(validStatus(status));
        InterfaceInfo interfaceInfo=new InterfaceInfo();
        BeanUtils.copyProperties(addInterfaceInfoDto,interfaceInfo);
        interfaceInfo.setUserId(uid);

        return this.save(interfaceInfo);
    }

    @Override
    public Boolean updateInterface(UpdateInterfaceInfoDto updateInterfaceInfoDto) {

        //校验id是否存在
        Integer method = updateInterfaceInfoDto.getMethod();
        Integer status = updateInterfaceInfoDto.getStatus();
        BusinessException.throwIfNot(MybatisUtils.existCheck(this,Map.of("id",updateInterfaceInfoDto.getId())));
        BusinessException.throwIfNot(validMethod(method));
        BusinessException.throwIfNot(validStatus(status) );
        InterfaceInfo interfaceInfo=new InterfaceInfo();
        BeanUtils.copyProperties(updateInterfaceInfoDto,interfaceInfo);
        return this.updateById(interfaceInfo);
    }

    @Override
    public Boolean deleteInterface(List<Long> ids) {
         if(ObjectUtils.isEmpty(ids))return false;
        return this.update(new UpdateWrapper<InterfaceInfo>().set("delete_flag", CommonConstants.DELETED).in("id",ids));
    }

    @Override
    public Page<InterfaceInfoVo> pageInterfaceVo(PageInterfaceInfoDto pageInterfaceInfoDto) {
        Integer method = pageInterfaceInfoDto.getMethod();
        Integer status = pageInterfaceInfoDto.getStatus();
        String name = pageInterfaceInfoDto.getName();
        Integer pageNum = pageInterfaceInfoDto.getPageNum();
        Integer pageSize = pageInterfaceInfoDto.getPageSize();
        Page<InterfaceInfo> page = this.page(new Page<>(pageNum, pageSize), new QueryWrapper<InterfaceInfo>().like(!StringUtils.isEmpty(name), "name", name)
                .eq(validMethod(method), "method", method).eq(validStatus(status), "status", status)
                .eq("delete_flag",CommonConstants.NOT_DELETED));

        List<InterfaceInfo> records = page.getRecords();

        List<InterfaceInfoVo> interfaceInfoVos = INTERFACE_CONVERTER.toListInterfaceInfoVo(records);
        Page<InterfaceInfoVo> interfaceInfoVoPage=new Page<>(pageNum,pageSize,page.getTotal());
        interfaceInfoVoPage.setRecords(interfaceInfoVos);
        return interfaceInfoVoPage;
    }

    @Override
    public InterfaceInfoVo getInterfaceById(Long id) {

        InterfaceInfo byId = this.getById(id);
        return INTERFACE_CONVERTER.toInterfaceInfoVo(byId);
    }

    @Override
    public Boolean onlineInterface(Long id) {
        return this.update(new UpdateWrapper<InterfaceInfo>().eq("id",id).set("status",InterfaceInfoStatusEnum.ONLINE.getValue()));
    }

    @Override
    public Boolean offlineInterface(Long id) {
        return this.update(new UpdateWrapper<InterfaceInfo>().eq("id",id).set("status",InterfaceInfoStatusEnum.OFFLINE.getValue()));
    }

    @Override
    public String invokeinterface(InvokeInterfaceDto invokeInterfaceDto, String accessKey,String secretKey) {

        String requestParams = invokeInterfaceDto.getRequestParams();
        Long id = invokeInterfaceDto.getId();
        BusinessException.throwIfNot(MybatisUtils.existCheck(this,Map.of("id",id)));
        LmsApiClient lmsApiClient=new LmsApiClient(accessKey,secretKey);
        return lmsApiClient.testConnection();
    }


    private boolean validMethod(Integer method){
        List<Integer> values = MethodEnum.getValues();
        return !ObjectUtils.isEmpty(method)&&values.contains(method);
    }

    private boolean validStatus(Integer status){
        List<Integer> values = InterfaceInfoStatusEnum.getValues();
        return  !ObjectUtils.isEmpty(status)&&values.contains(status);
    }
}
