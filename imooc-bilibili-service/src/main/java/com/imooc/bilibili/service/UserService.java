package com.imooc.bilibili.service;

import com.imooc.bilibili.constant.UserConstant;
import com.imooc.bilibili.dao.UserDao;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.exception.ConditionExppection;
import com.imooc.bilibili.util.MD5Util;
import com.imooc.bilibili.util.RSAUtil;
import com.imooc.bilibili.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(user.getPhone())){
            throw new ConditionExppection("手机号不为空");
        }
        User dbUser = getUserByPhone(phone);
        if (dbUser != null){
            throw new ConditionExppection("手机号已被注册");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionExppection("密码解密失败!");
        }
        String md5Password = MD5Util.sign(rawPassword,salt,"UTF-8");
        user.setSalt(salt);
        user.setPassword(password);
        user.setCreateTime(now);
        userDao.addUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);
    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    public String loginUser(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(user.getPhone())){
            throw new ConditionExppection("手机号不为空");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser == null){
            throw new ConditionExppection("用户不存在");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionExppection("密码解密失败");
        }
        String salt = dbUser.getSalt();
        String md5Pass2word = MD5Util.sign(rawPassword,salt,"UTF-8");
        if (!md5Pass2word.equals(dbUser.getPassword())){
            throw new ConditionExppection("密码错误!");
        }
        String token = TokenUtil.generateToken(dbUser.getId());
        return token;
    }

    public User getUserInfo(Long userId) {
        User userById = userDao.getUserById(userId);
        return userById;
    }

    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfos(userInfo);

    }
}
