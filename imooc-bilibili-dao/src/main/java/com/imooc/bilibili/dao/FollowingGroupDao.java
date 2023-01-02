package com.imooc.bilibili.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowingGroupDao extends BaseMapper<FollowingGroup>{


    FollowingGroup getByType(String type);

    List<FollowingGroup> getByUserId(Long userId);
}
