package com.imooc.bilibili.dao;

import com.alibaba.fastjson.JSONObject;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {


    User getUserByPhone(String phone);

    Integer addUser(User user);

    void addUserInfo(UserInfo userInfo);

    User getUserById(Long id);

    void updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds();

    Integer pageCountUserInfos(JSONObject params);

    List<UserInfo> pageListUserInfos(JSONObject params);
}
