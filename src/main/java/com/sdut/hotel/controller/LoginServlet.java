package com.sdut.hotel.controller;

import com.sdut.hotel.pojo.User;
import com.sdut.hotel.service.IUserService;
import com.sdut.hotel.service.impl.UserServiceImpl;
import com.sdut.hotel.utils.JSONResult;
import com.sdut.hotel.utils.JSONUtil;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/22
//Time: 19:43
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private IUserService userService = new UserServiceImpl();
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "login":
                login(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
        }
    }


    private void login(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("LoginServlet.login");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String code = req.getParameter("code");
        //先判断验证码是不是正确
        //验证码错误，返回一个错误提示信息
        //验证码正确，再验证用户名密码
        HttpSession session = req.getSession();
        String codeInSession = (String) session.getAttribute("codeInSession");
        if (StringUtils.isEmpty(code) || !codeInSession.equalsIgnoreCase(code)){
            JSONUtil.obj2Json(JSONResult.error("验证码为空或错误"),resp);
            return;
        }

        User user = userService.login(name,password);
        if(user != null){
            //登录上之后，把这个凭证保存在session中
            session.setAttribute("user",user);
            JSONUtil.obj2Json(JSONResult.ok("登录成功"),resp);
        }else {
            JSONUtil.obj2Json(JSONResult.error("用户名或密码错误"),resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("LoginServlet.logout");
        //注销：删除在session中的凭证，然后重定向到登录页面
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.invalidate();

        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }


}
