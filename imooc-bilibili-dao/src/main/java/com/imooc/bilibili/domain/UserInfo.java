package com.imooc.bilibili.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String nick;
    private String avatar;
    private String gender;
    private String birth;
    private Date createTime;
    private Date updateTime;
    private Boolean followed;

}
