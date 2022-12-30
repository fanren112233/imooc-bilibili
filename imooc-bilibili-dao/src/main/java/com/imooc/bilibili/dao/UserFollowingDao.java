package com.imooc.bilibili.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowingDao extends BaseMapper<UserFollowing> {

}
