package com.gongmc.cupid.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.gongmc.cupid.model.dto.HaloConst.USER_SESSION_KEY;

/**
 * <pre>
 *     后台登录控制器
 * </pre>
 *
 *
 * @date : 2017/12/13
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Object obj = request.getSession().getAttribute(USER_SESSION_KEY);
        //如果user不为空则放行
        if (null != obj) {
            return true;
        }
        //否则拦截并跳转到登录
        response.sendRedirect("/admin/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
