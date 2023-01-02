package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserFollowing {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long followingId;

    private Long groupId;

    private Date createTime;

    private UserInfo userInfo;
}
