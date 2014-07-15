package com.mua.xjy.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "friend")
public class FriendDto implements Serializable{
    private int userId;
    private int friendId;
    private int status;
    private String specialName;

    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    @Override
    public String toString() {
        return "FriendDto{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", status=" + status +
                ", specialName='" + specialName + '\'' +
                '}';
    }
}
