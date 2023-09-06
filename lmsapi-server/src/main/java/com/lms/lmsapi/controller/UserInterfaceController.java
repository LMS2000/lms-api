package com.lms.lmsapi.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.lmsapi.annotation.AuthCheck;
import com.lms.lmsapi.constants.CommonConstants;
import com.lms.lmsapi.constants.UserConstants;

import com.lms.lmsapi.entity.dto.userInterfaceInfo.PageUserInterfaceInfoDto;
import com.lms.lmsapi.entity.vo.userInterfaceInfo.UserInterfaceInfoVo;
import com.lms.lmsapi.exception.BusinessException;
import com.lms.lmsapi.service.IUserInterfaceInfoService;
import com.lms.lmsapi.service.IUserService;
import com.lms.lmsapi.utils.MybatisUtils;
import com.lms.lmscommon.model.entity.UserInterfaceInfo;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/userInterfaceInfo")
@EnableResponseAdvice
@Api(description = "用户接口管理")
public class UserInterfaceController {
    @Resource
    private IUserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private IUserService userService;



    @PostMapping("/add/{interfaceId}")
    @ApiOperation("用户添加接口使用")
    public Boolean addUserInterface(HttpServletRequest request, @PathVariable("interfaceId") Long  interfaceId){
        Long uid = userService.getLoginUser(request).getUid();
        return userInterfaceInfoService.saveUserInterface(uid,interfaceId);
    }



    @PostMapping("/page")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    @ApiOperation("分页查询用户使用接口信息")
    public Page<UserInterfaceInfoVo> pageUserInterfaceInfo(@RequestBody PageUserInterfaceInfoDto pageInterfaceInfoDto){
        return userInterfaceInfoService.pageUseInterfaceInfo(pageInterfaceInfoDto);
    }

    @PostMapping("/page/user")
    @ApiOperation("用户获取自己的使用接口信息")
    public Page<UserInterfaceInfoVo> pageCurentUserInterfaceInfo(HttpServletRequest request,@RequestBody PageUserInterfaceInfoDto pageUserInterfaceInfoDto){
        Long uid = userService.getLoginUser(request).getUid();
        return userInterfaceInfoService.pageCurrentInterfaceInfo(uid,pageUserInterfaceInfoDto);
    }


    @PostMapping("/delete")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    @ApiOperation("删除用户使用接口信息")
    public Boolean deleteUserInterface(@RequestParam("ids")List<Long> ids){
               return userInterfaceInfoService.update(new UpdateWrapper<UserInterfaceInfo>()
                       .in("id",ids).set("delete_flag", CommonConstants.DELETED));
    }

    @PostMapping("/renewal/{id}")
    @ApiOperation("续期使用接口")
    public Boolean renewalInterface(@Positive(message = "id不合法") @PathVariable("id") Long id){
        BusinessException.throwIfNot(MybatisUtils.existCheck(
                userInterfaceInfoService, Map.of("id",id,"delete_flag",CommonConstants.NOT_DELETED)));
        return userInterfaceInfoService.update(new UpdateWrapper<UserInterfaceInfo>()
                .eq("id",id).set("left_num",100));
    }


    @PostMapping("/update/{id}/{status}")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    @ApiOperation("修改用户使用接口处状态")
    public Boolean  updateStatus(@PathVariable("id") Long id,@PathVariable("status") Integer status){
       BusinessException.throwIfNot(!ObjectUtils.isEmpty(status)&&(status.equals(0)||status.equals(1)));
       return userInterfaceInfoService.update(new UpdateWrapper<UserInterfaceInfo>().set("status",status).eq("id",id));
    }



}
