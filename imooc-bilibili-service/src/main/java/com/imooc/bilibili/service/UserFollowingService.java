package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.bilibili.constant.UserConstant;
import com.imooc.bilibili.dao.UserFollowingDao;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.exception.ConditionExppection;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFollowingService {

    @Autowired
    private UserFollowingDao userFollowingDao;

    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;


    public void addUserFollowings(UserFollowing userFollowing) {
        Long groupId = userFollowing.getGroupId();
        if (groupId == null) {
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getUserId());
        } else {
            FollowingGroup byId = followingGroupService.getById(groupId);
            if (byId == null) {
                throw new ConditionExppection("关注分组不存在");
            }
            Long followingId = userFollowing.getFollowingId();
            User user = userService.getUserById(followingId);
            if (user == null) {
                throw new ConditionExppection("关注的用户不存在");
            }
            userFollowingDao.delete(new QueryWrapper<UserFollowing>().eq("userId",
                    userFollowing.getUserId()).eq("followingId", followingId));
            userFollowing.setCreateTime(new Date());
            userFollowingDao.insert(userFollowing);
        }
    }
    // 第一步：获取关注的用户列表
    // 第二步：根据关注用户的id查询关注用户的基本信息
    // 第三步：将关注用户按关注分组进行分类
    public List<FollowingGroup> getUserFollowings(Long userId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId",userId);
        List<UserFollowing> list = userFollowingDao.selectList(queryWrapper);
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }
        for(UserFollowing userFollowing : list){
            for(UserInfo userInfo : userInfoList){
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for(FollowingGroup group : groupList){
            List<UserInfo> infoList = new ArrayList<>();
            for(UserFollowing userFollowing : list){
                if(group.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            group.setFollowingUserInfoList(infoList);
            result.add(group);
        }
        return result;
    }
    public List<UserFollowing> getUserFans(Long userId){
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if (fanIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId",userId);
        List<UserFollowing> followingList = userFollowingDao.selectList(queryWrapper);
        for (UserFollowing fan :fanList
             ) {
            for (UserInfo userInfo : userInfoList) {
                if (fan.getUserId().equals(userInfo.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.addFollowingGroup(followingGroup);
        return null;
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }

    public List<UserInfo> checkFollowingState(List<UserInfo> userInfolist, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId",userId);
        List<UserFollowing> userFollowingList = userFollowingDao.selectList(queryWrapper);
        for (UserInfo userInfo: userInfolist
             ) {
            userInfo.setFollowed(false);
            for (UserFollowing userFollowing:userFollowingList
                 ) {
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(true);
                }
            }
        }
        return null;
    }
}
