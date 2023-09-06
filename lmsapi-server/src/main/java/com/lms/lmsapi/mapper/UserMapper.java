package com.lms.lmsapi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lms.lmscommon.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {


}
