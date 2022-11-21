package com.imooc.bilibili.handler;


import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.exception.ConditionExppection;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExpectionHandler {


    @ExceptionHandler(value = Exception.class)
//    @RequestBody
    public JsonResponse<String> commonExpectionHandler(HttpServletRequest request, Exception e){
            String errorMsg = e.getMessage();
            if (e instanceof ConditionExppection){
                String errorCode = ((ConditionExppection)e).getCode();
                return new JsonResponse<>(errorCode,errorMsg);
            }else {
                return new JsonResponse<>("500",errorMsg);
            }



    }
}
