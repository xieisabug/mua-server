package com.mua.xjy.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/dispatcher-servlet.xml")
public class FriendVoDaoTest {

    @Autowired
    public FriendVoDao friendVoDao;

    @Test
    public void testListFriend(){
        try {
            /*String hql = "select u.id as id, friend.friendId as friendId, u.name as name,u.level as level,friend.status as status,friend.specialName as specialName " +
                    " from FriendDto as friend LEFT JOIN UserDto as u on u.id = friend.userId ";
            System.out.println(friendVoDao.list(hql + " where id=" + 1));*/
            System.out.println(friendVoDao.getFriend(1));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
