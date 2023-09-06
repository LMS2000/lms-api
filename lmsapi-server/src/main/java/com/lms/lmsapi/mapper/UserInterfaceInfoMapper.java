package com.lms.lmsapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lms.lmscommon.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.yupi.project.model.entity.UserInterfaceInfo
 */
@Mapper
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




