package com.mua.xjy.dto;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_friend")
public class FriendVo {
    private int id;
    private int friendId;
    private String name;
    private int level;
    private int status;
    private String specialName;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        return "Friend{" +
                "id=" + id +
                ", friendId=" + friendId +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", status=" + status +
                ", specialName='" + specialName + '\'' +
                '}';
    }
}
