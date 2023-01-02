package com.imooc.bilibili.api;

import com.imooc.bilibili.service.UserMomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMomentsApi {

    @Autowired
    private UserMomentService userMomentService;

}
