package com.mua.xjy.dao;

import com.mua.xjy.dao.base.BaseDao;
import com.mua.xjy.dao.base.IBaseDao;
import com.mua.xjy.dto.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<UserDto> implements IBaseDao<UserDto> {

}
