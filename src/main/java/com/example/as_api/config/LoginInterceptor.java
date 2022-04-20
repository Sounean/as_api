package com.example.as_api.config;

import com.example.as_api.util.UserRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> clazz = handlerMethod.getBeanType();
        Method m = handlerMethod.getMethod();
        //不需要登录就可以访问的接口（如果某个类、方法没有NeedLogin注解，就表示他们是不需要登录的）
        if (!clazz.isAnnotationPresent(NeedLogin.class) && !m.isAnnotationPresent(NeedLogin.class)) {
            return true;
        }
        // 通过上面的判断，下面的必然都是需要登录权限的。
        //  用户登陆过返回true，否则返回401(代表访问被禁止或没授权)
        if (UserRedisUtil.checkUser(redisTemplate, request)) {
            return true;
        } else {
            response.setStatus(401);
            response.setContentType("text/html;charset=utf-8");//fix response 防止乱码
            response.getWriter().write("Please login first.");
        }
        return false;
    }
}
