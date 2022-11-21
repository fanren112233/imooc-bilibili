package com.imooc.bilibili.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private Long userId;
    private String nick;
    private String avatar;
    private String gender;
    private String birth;
    private Date createTime;
    private Date updateTime;


}
