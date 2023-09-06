package com.lms.lmsapi.entity.factory;



import com.lms.lmsapi.entity.dto.user.UserDto;
import com.lms.lmsapi.entity.vo.user.UserVo;
import com.lms.lmscommon.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class UserFactory {
    public static final UserConverter USER_CONVERTER= Mappers.getMapper(UserConverter.class);
    @Mapper
   public interface UserConverter {
        @Mappings({
                @Mapping(target = "uid",ignore = true),
        }
        )
        User toUser(UserDto userDto);
        UserVo toUserVo(User user);
        List<UserVo> toListUserVo(List<User> user);
    }
}
