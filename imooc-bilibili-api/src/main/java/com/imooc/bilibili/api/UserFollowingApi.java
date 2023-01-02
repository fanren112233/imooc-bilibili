package com.imooc.bilibili.api;

import com.imooc.bilibili.api.support.UserSupport;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserFollowingApi {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private UserSupport userSupport;


    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId =userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId =userSupport.getCurrentUserId();
        List<FollowingGroup> userFollowings = userFollowingService.getUserFollowings(userSupport.getCurrentUserId());
        return new JsonResponse<>(userFollowings);
    }

    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans(@RequestBody UserFollowing userFollowing){
        Long userId =userSupport.getCurrentUserId();
        List<UserFollowing> userFans = userFollowingService.getUserFans(userSupport.getCurrentUserId());
        return new JsonResponse<>(userFans);
    }
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }
    @GetMapping("user-following-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroups(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> userFollowings = userFollowingService.getUserFollowingGroups(userId);
        return new JsonResponse<>(userFollowings);
    }
}
