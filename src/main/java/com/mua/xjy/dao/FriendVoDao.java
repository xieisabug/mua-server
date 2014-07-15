package com.mua.xjy.dao;

import com.mua.xjy.dao.base.BaseDao;
import com.mua.xjy.dao.base.IBaseDao;
import com.mua.xjy.dto.FriendVo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendVoDao extends BaseDao<FriendVo> implements IBaseDao<FriendVo>{
    public List<FriendVo> getFriend(int userId){
        String hql = "select u.id, friend.friendId, u.name,u.level,friend.status,friend.specialName " +
                " from friend LEFT JOIN u_user u on u.id = friend.userId where u.id="+userId;
        Session session = getSessionFactory().openSession();
        Query query = session.createSQLQuery(hql);
        List list = query.list();
        List<FriendVo> friendVoList = new ArrayList<FriendVo>();
        for (Object aList : list) {
            Object[] o = (Object[]) aList;
            FriendVo friendVo = new FriendVo();
            int id = Integer.parseInt(String.valueOf(o[0]));
            int friendId = Integer.parseInt(String.valueOf(o[1]));
            String name = String.valueOf(o[2]);
            int level = Integer.parseInt(String.valueOf(o[3]));
            int status = Integer.parseInt(String.valueOf(o[4]));
            String specialName = String.valueOf(o[5]);
            friendVo.setId(id);
            friendVo.setFriendId(friendId);
            friendVo.setName(name);
            friendVo.setLevel(level);
            friendVo.setStatus(status);
            friendVo.setSpecialName(specialName);
            friendVoList.add(friendVo);
        }
        return friendVoList;
    }

    public FriendVo get(int userId,int friendId){
        String hql = "select u.id, friend.friendId, u.name,u.level,friend.status,friend.specialName " +
                " from friend LEFT JOIN u_user u on u.id = friend.userId where u.id="+userId+" and friend.friendId="+friendId;
        Session session = getSessionFactory().openSession();
        Query query = session.createSQLQuery(hql);
        List list = query.list();
        FriendVo friendVo = null;
        for (Object aList : list) {
            Object[] o = (Object[]) aList;
            friendVo = new FriendVo();
            int id = Integer.parseInt(String.valueOf(o[0]));
            String name = String.valueOf(o[2]);
            int level = Integer.parseInt(String.valueOf(o[3]));
            int status = Integer.parseInt(String.valueOf(o[4]));
            String specialName = String.valueOf(o[5]);
            friendVo.setId(id);
            friendVo.setFriendId(friendId);
            friendVo.setName(name);
            friendVo.setLevel(level);
            friendVo.setStatus(status);
            friendVo.setSpecialName(specialName);
        }
        return friendVo;
    }
}
