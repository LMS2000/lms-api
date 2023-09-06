package com.lms.lmsapi.entity.factory;

import com.lms.lmsapi.entity.dto.user.UserDto;
import com.lms.lmsapi.entity.vo.user.UserVo;
import com.lms.lmsapi.entity.vo.userInterfaceInfo.UserInterfaceInfoVo;
import com.lms.lmscommon.model.entity.User;
import com.lms.lmscommon.model.entity.UserInterfaceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class UserInterfaceFactory {

    public static final UserInterfaceConverter USER_INTERFACE_CONVERTER= Mappers.getMapper(UserInterfaceConverter.class);
    @Mapper
    public interface UserInterfaceConverter {
        @Mappings({

        }
        )
        UserInterfaceInfoVo toUserInterfaceVo(UserInterfaceInfo userInterfaceInfo);
        List<UserInterfaceInfoVo> toListUserInterfaceVo(List<UserInterfaceInfo> userInterfaceInfos);
    }
}
