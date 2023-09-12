package com.lms.lmsapi.controller;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleReturningClause;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.lmsapi.annotation.AuthCheck;
import com.lms.lmsapi.constants.UserConstants;
import com.lms.lmsapi.entity.dto.interfaceInfo.AddInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.InvokeInterfaceDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.PageInterfaceInfoDto;
import com.lms.lmsapi.entity.dto.interfaceInfo.UpdateInterfaceInfoDto;
import com.lms.lmsapi.entity.vo.interfaceInfo.InterfaceInfoVo;
import com.lms.lmsapi.entity.vo.user.LoginUserVo;
import com.lms.lmsapi.service.IInterfaceInfoService;
import com.lms.lmsapi.service.IUserService;
import com.lms.lmscommon.model.enums.InterfaceInfoStatusEnum;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/interfaceInfo")
@EnableResponseAdvice
@Api(description = "接口管理")
public class InterfaceInfoController {


    @Resource
    private IInterfaceInfoService interfaceInfoService;

    @Resource
    private IUserService userService;

    /**
     * 添加接口信息
     *
     * @param request
     * @param addInterfaceInfoDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("添加接口信息")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    public Boolean saveInterfaceInfo(HttpServletRequest request, @Validated @RequestBody AddInterfaceInfoDto addInterfaceInfoDto) {
        Long uid = userService.getLoginUser(request).getUid();
        return interfaceInfoService.saveInterface(addInterfaceInfoDto, uid);
    }

    @PostMapping("/update")
    @ApiOperation("修改接口信息")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    public Boolean updateInterfaceInfo(@Validated @RequestBody UpdateInterfaceInfoDto updateInterfaceInfoDto) {
        return interfaceInfoService.updateInterface(updateInterfaceInfoDto);
    }


    @PostMapping("/page")
    @ApiOperation("分页查询接口信息列表")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    public Page<InterfaceInfoVo> page(@RequestBody PageInterfaceInfoDto pageInterfaceInfoDto) {
        return interfaceInfoService.pageInterfaceVo(pageInterfaceInfoDto);
    }

    @PostMapping("/page/user")
    @ApiOperation("分页查询上线的接口")
    public Page<InterfaceInfoVo> pageUser(@RequestBody PageInterfaceInfoDto pageInterfaceInfoDto){
        pageInterfaceInfoDto.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        return interfaceInfoService.pageInterfaceVo(pageInterfaceInfoDto);
    }

    @PostMapping("/delete")
    @ApiOperation("批量删除接口信息")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    public Boolean deleteBatchInterfaceInfo(@RequestParam("ids") List<Long> ids) {
        return interfaceInfoService.deleteInterface(ids);
    }


    @GetMapping("/{id}")
    @ApiOperation("根绝id获取接口信息")
    public InterfaceInfoVo getInterfaceById(@Positive(message = "id不合法") @PathVariable("id") Long id) {
        return interfaceInfoService.getInterfaceById(id);
    }



    @PostMapping("/changeStatus/{id}/{status}")
    @AuthCheck(anyRole = UserConstants.ADMIN_ROLE)
    @ApiOperation("发布或者下线接口")
    public Boolean changeInterfaceStatus(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        if(InterfaceInfoStatusEnum.ONLINE.getValue().equals(status)){
            return interfaceInfoService.onlineInterface(id);
        }else if(InterfaceInfoStatusEnum.OFFLINE.getValue().equals(status)){
            return interfaceInfoService.offlineInterface(id);
        }
        return false;
    }



    @PostMapping("/invoke")
    @ApiOperation("调用测试接口")
    public String invokeInterface(@RequestBody InvokeInterfaceDto invokeInterfaceDto,HttpServletRequest request){
        LoginUserVo loginUser = userService.getLoginUser(request);
        return interfaceInfoService.invokeinterface(invokeInterfaceDto,loginUser.getAccessKey(),loginUser.getSecretKey());
    }
}
