package org.byron4j.ssm_core.batis.basic.mapper;

import org.apache.ibatis.annotations.Param;
import org.byron4j.ssm_core.batis.basic.module.User;

public interface UserMapper {
	
	User selectUser(@Param(value = "id") int id);
}
