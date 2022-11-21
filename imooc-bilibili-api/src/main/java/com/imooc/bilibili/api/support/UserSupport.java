package com.imooc.bilibili.api.support;

import com.imooc.bilibili.exception.ConditionExppection;
import com.imooc.bilibili.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {
      public Long getCurrentUserId(){
          ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
          String token = requestAttributes.getRequest().getHeader("token");
          Long userId = TokenUtil.verifuToken(token);
            if (userId < 0){
                throw new ConditionExppection("非法用户!");
            }
      }



}