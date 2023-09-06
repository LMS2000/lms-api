package com.lms.lmsapi.entity.factory;


import com.lms.lmsapi.entity.dto.user.UserDto;
import com.lms.lmsapi.entity.vo.interfaceInfo.InterfaceInfoVo;
import com.lms.lmsapi.entity.vo.user.UserVo;
import com.lms.lmscommon.model.entity.InterfaceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class InterfaceFactory {

    public static final InterfaceConverter INTERFACE_CONVERTER= Mappers.getMapper(InterfaceConverter.class);
    @Mapper
    public interface InterfaceConverter {
        @Mappings({

        }
        )
        InterfaceInfoVo toInterfaceInfoVo(InterfaceInfo interfaceInfo);
        List<InterfaceInfoVo> toListInterfaceInfoVo(List<InterfaceInfo> interfaceInfos);
    }
}
