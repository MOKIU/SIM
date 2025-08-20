package com.mokiu.interceptor;

import com.alibaba.fastjson2.JSON;
import com.mokiu.common.utils.JwtUtil;
import com.mokiu.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 这个 JwtValidateInterceptor 类是一个 基于 JWT 的请求拦截器，用于保护需要认证的 API 接口。
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 请求中获取token
        String token = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证" + token);
        if (token != null){
            try {
                // 将token解析,如果解析失败抛异常不会打印成功日志
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI() + "验证通过");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI() + "验证失败，禁止访问");
        // 返回失败信息
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(20003,"jwt令牌无效，请重新登录")));
        return false; // 拦截
    }
}

// 1. 核心职责
//Token 验证：检查请求头中的 X-Token 是否有效。
//访问控制：拦截未携带有效 Token 的请求，返回标准化错误信息。
//日志记录：记录验证过程和结果，便于调试。