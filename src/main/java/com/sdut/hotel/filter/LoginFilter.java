package com.sdut.hotel.filter;

import com.alibaba.druid.util.ServletPathMatcher;
import com.mysql.cj.Session;
import com.sdut.hotel.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpRetryException;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/23
//Time: 08:53
//  /*代表拦截所有的请求
//@WebFilter(filterName = "login" , urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoginFilter.doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String servletPath = httpServletRequest.getServletPath();
        System.out.println("servletPath:" + servletPath);

        //这些操作是要完成登录操作，不需要执行后边已经登录的验证
        //例如在没有登录的情况下 到登录界面去登录，也会被loginFilter拦截，
        //就需要判读是否已经登录，没有登录，又重定向到登录界面，形成循环
        if (servletPath.endsWith(".jpg")
                || servletPath.endsWith(".png")
                || servletPath.endsWith(".js")
                || servletPath.endsWith(".css")
                || servletPath.equals("/login.jsp")
                || servletPath.equals("/auth")
                || servletPath.equals("/login")){
            chain.doFilter(request,response);
            return;
        }


        //判断session之中有没有登录的凭证，如果没有登录，重定向到登录界面
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
            return;
        }

        //如果希望这个过滤器继续往后执行就要执行以下代码
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter.destroy");
    }
}
