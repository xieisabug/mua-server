package com.mua.xjy.service;

import com.mua.xjy.dao.UserDao;
import com.mua.xjy.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Integer add(UserDto userDto){
        return (Integer) userDao.add(userDto);
    }

    public void delete(int id){
        userDao.delete(id);
    }

    public UserDto load(int id) {
        try {
            return userDao.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(UserDto userDto) {
        return userDao.update(userDto);
    }

    public UserDto login(String username, String password){
        List<UserDto> list;
        try {
            list = userDao.list("from UserDto where username=? and password=?",new Object[]{username, password});
            if (list.size() == 1) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
