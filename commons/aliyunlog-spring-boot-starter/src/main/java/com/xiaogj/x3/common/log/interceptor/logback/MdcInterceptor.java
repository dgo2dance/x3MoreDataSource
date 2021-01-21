package com.xiaogj.x3.common.log.interceptor.logback;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 拦截日志监控 <br>
 * @date: 2020/9/10 11:28 <br>
 * @author: Frank <br>
 * @version: 1.0 <br>
 */
public class MdcInterceptor implements HandlerInterceptor {

    private final String COMPANY_ID = "companyId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String companyId = request.getHeader(COMPANY_ID);
        MDC.put(COMPANY_ID, companyId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(COMPANY_ID);
    }
}