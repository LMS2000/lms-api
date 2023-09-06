package com.lms.lmsapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lms.lmsapi.constants.CommonConstants;
import com.lms.lmsapi.entity.dto.interfaceInfo.PageInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.userInterfaceInfo.PageUserInterfaceInfoDto;
import com.lms.lmsapi.entity.vo.interfaceInfo.InterfaceInfoVo;
import com.lms.lmsapi.entity.vo.user.UserVo;
import com.lms.lmsapi.entity.vo.userInterfaceInfo.UserInterfaceInfoVo;
import com.lms.lmsapi.exception.BusinessException;
import com.lms.lmsapi.mapper.UserInterfaceInfoMapper;
import com.lms.lmsapi.service.IInterfaceInfoService;
import com.lms.lmsapi.service.IUserInterfaceInfoService;
import com.lms.lmsapi.service.IUserService;
import com.lms.lmsapi.utils.MybatisUtils;
import com.lms.lmscommon.model.entity.InterfaceInfo;
import com.lms.lmscommon.model.entity.User;
import com.lms.lmscommon.model.entity.UserInterfaceInfo;
import com.lms.lmscommon.model.enums.InterfaceInfoStatusEnum;
import com.lms.lmscommon.model.enums.MethodEnum;
import com.mysql.cj.protocol.x.XProtocolRowInputStream;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lms.lmsapi.entity.factory.InterfaceFactory.INTERFACE_CONVERTER;
import static com.lms.lmsapi.entity.factory.UserFactory.USER_CONVERTER;
import static com.lms.lmsapi.entity.factory.UserInterfaceFactory.USER_INTERFACE_CONVERTER;

/**
 *  用户接口管理
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements IUserInterfaceInfoService {

    @Resource
    private IInterfaceInfoService interfaceInfoService;


    @Resource
    private IUserService userService;
    @Override
    public Boolean saveUserInterface(Long uid, Long interfaceId) {
        BusinessException.throwIfNot(MybatisUtils.existCheck(interfaceInfoService, Map.of("id",interfaceId)));
        BusinessException.throwIf(MybatisUtils.existCheck(this,Map.of("user_id",uid,"interface_info_id",interfaceId)));
      return   this.save(UserInterfaceInfo.builder().interfaceInfoId(interfaceId).userId(uid).leftNum(100).build());

    }

    @Override
    public Page<UserInterfaceInfoVo> pageUseInterfaceInfo(PageUserInterfaceInfoDto pageInterfaceInfoDto) {
        Integer pageSize = pageInterfaceInfoDto.getPageSize();
        Integer pageNum = pageInterfaceInfoDto.getPageNum();
        Integer method = pageInterfaceInfoDto.getMethod();
        String name = pageInterfaceInfoDto.getName();
        Integer status = pageInterfaceInfoDto.getStatus();

        //获取所需要的接口信息和用户信息的map，然后遍历的时候根据记录的id注入信息

        List<InterfaceInfo>  list = interfaceInfoService.list(new QueryWrapper<InterfaceInfo>().eq(validMethod(method), "method", method)
                    .like(!StringUtils.isEmpty(name), "name", name));

        List<Long> interfaceIds = list.stream().map(InterfaceInfo::getId).collect(Collectors.toList());
        List<InterfaceInfoVo> interfaceInfoVos = INTERFACE_CONVERTER.toListInterfaceInfoVo(list);
        Map<Long, InterfaceInfoVo> interfaceInfoVoMap = interfaceInfoVos.stream().collect(Collectors.toMap(InterfaceInfoVo::getId, Function.identity()));
        Map<Long, UserVo> userVoMap = USER_CONVERTER.toListUserVo(userService.list()).stream().collect(Collectors.toMap(UserVo::getUid, Function.identity()));
        if(ObjectUtils.isEmpty(interfaceIds)){
            return new Page<>();
        }
        Page<UserInterfaceInfo>  userInterfaceInfoPage = this.page(new Page<>(pageNum, pageSize), new QueryWrapper<UserInterfaceInfo>().eq(validStatus(status), "status", status)
                .eq("delete_flag", CommonConstants.NOT_DELETED).in("interface_info_id",interfaceIds));

        List<UserInterfaceInfo> records = userInterfaceInfoPage.getRecords();
        List<UserInterfaceInfoVo> userInterfaceInfoVos = USER_INTERFACE_CONVERTER.toListUserInterfaceVo(records);
        List<UserInterfaceInfoVo> collect = userInterfaceInfoVos.stream().map(userInterfaceInfoVo -> {
            userInterfaceInfoVo.setInterfaceInfoVo(interfaceInfoVoMap.getOrDefault(userInterfaceInfoVo.getInterfaceInfoId(), null));
            userInterfaceInfoVo.setUserVo(userVoMap.getOrDefault(userInterfaceInfoVo.getUserId(), null));
            return userInterfaceInfoVo;
        }).collect(Collectors.toList());
        Page<UserInterfaceInfoVo> userInterfaceInfoVoPage=new Page<>(pageNum,pageSize,userInterfaceInfoPage.getTotal());
        userInterfaceInfoVoPage.setRecords(collect);

        return userInterfaceInfoVoPage;
    }

    @Override
    public Page<UserInterfaceInfoVo> pageCurrentInterfaceInfo(Long uid, PageUserInterfaceInfoDto pageInterfaceInfoDto) {

        Integer pageSize = pageInterfaceInfoDto.getPageSize();
        Integer pageNum = pageInterfaceInfoDto.getPageNum();
        Integer method = pageInterfaceInfoDto.getMethod();
        String name = pageInterfaceInfoDto.getName();
        Integer status = pageInterfaceInfoDto.getStatus();

        //获取所需要的接口信息和用户信息的map，然后遍历的时候根据记录的id注入信息
        List<InterfaceInfo> list = interfaceInfoService.list(new QueryWrapper<InterfaceInfo>().eq(validMethod(method), "method", method)
                .like(!StringUtils.isEmpty(name), "name", name));
        List<InterfaceInfoVo> interfaceInfoVos = INTERFACE_CONVERTER.toListInterfaceInfoVo(list);
        Map<Long, InterfaceInfoVo> interfaceInfoVoMap = interfaceInfoVos.stream().collect(Collectors.toMap(InterfaceInfoVo::getId, Function.identity()));

        Page<UserInterfaceInfo> userInterfaceInfoPage = this.page(new Page<>(pageNum, pageSize), new QueryWrapper<UserInterfaceInfo>().eq(validStatus(status), "status", status)
                .eq("delete_flag", CommonConstants.NOT_DELETED).eq("user_id",uid));
        List<UserInterfaceInfo> records = userInterfaceInfoPage.getRecords();
        List<UserInterfaceInfoVo> userInterfaceInfoVos = USER_INTERFACE_CONVERTER.toListUserInterfaceVo(records);
        List<UserInterfaceInfoVo> collect = userInterfaceInfoVos.stream().map(userInterfaceInfoVo -> {
            userInterfaceInfoVo.setInterfaceInfoVo(interfaceInfoVoMap.getOrDefault(userInterfaceInfoVo.getInterfaceInfoId(), null));
            return userInterfaceInfoVo;
        }).collect(Collectors.toList());
        Page<UserInterfaceInfoVo> userInterfaceInfoVoPage=new Page<>(pageNum,pageSize,userInterfaceInfoPage.getTotal());
        userInterfaceInfoVoPage.setRecords(collect);
        return userInterfaceInfoVoPage;
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




