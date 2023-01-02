package com.imooc.bilibili.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFollowingDao extends BaseMapper<UserFollowing> {

    List<UserFollowing> getUserFans(Long userId);
}
