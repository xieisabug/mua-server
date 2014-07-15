package com.mua.xjy.service;

import com.mua.xjy.dao.FriendDao;
import com.mua.xjy.dao.FriendVoDao;
import com.mua.xjy.dao.UserDao;
import com.mua.xjy.dto.FriendDto;
import com.mua.xjy.dto.FriendVo;
import com.mua.xjy.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendVoDao friendVoDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UserDao userDao;

    /**
     * 查询好友列表
     * @param userId 指定的用户
     * @return 返回好友列表
     */
    public List<FriendVo> list(int userId) {
        try {
            return friendVoDao.getFriend(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FriendVo>();
        }
    }

    /**
     * 添加一个好友
     * @param friendDto 好友实例
     * @return 返回添加的好友
     */
    public UserDto add(FriendDto friendDto){
        int id = (Integer)friendDao.add(friendDto);
        return userDao.load(id);
    }

    public FriendVo get(int userId, int friendId){
        return friendVoDao.get(userId,friendId);
    }
}
