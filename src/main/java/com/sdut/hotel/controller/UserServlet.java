package com.sdut.hotel.controller;

import com.sdut.hotel.pojo.User;
import com.sdut.hotel.service.IUserService;
import com.sdut.hotel.service.impl.UserServiceImpl;
import com.sdut.hotel.utils.JSONResult;
import com.sdut.hotel.utils.JSONUtil;
import com.sdut.hotel.utils.LayUITableResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/20
//Time: 10:47
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private IUserService userService = new UserServiceImpl();

    //http://localhost:8081/hotel/?method
    //http://localhost:8081/hotel/?deleteById&id=1
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method){
            case "selectAll":
                selectAll(req,resp);
                break;
            case "deleteById":
                deleteById(req,resp);
                break;
            case "selectByPage":
                selectByPage(req,resp);
                break;

        }


    }
    //https://www.layuiweb.com/demo/table/user/-page=1&limit=30.js?page=1&limit=10
    ///user?method=selectById&page=1&limit=10
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("UserServlet.selectByPage");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        LayUITableResult layUITableResult = userService.selectByPage(page,limit);
        JSONUtil.obj2Json(layUITableResult,resp);

    }



    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("UserServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = userService.deleteById(Integer.parseInt(id));
        //删除完了之后应该重定向查找所有界面  /hotel/user?method=selectAll
//        resp.sendRedirect(req.getContextPath() + "/user?method=selectAll");
        if (isSuccess){
            JSONUtil.obj2Json(JSONResult.ok("删除成功"),resp);
        }else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"),resp);
        }



    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UserServlet.selectAll");
        List<User> list = userService.selectAll();
        // 把数据放到req这个域对象
        req.setAttribute("list",list);
        // 转发到user_list.jsp页面进行展示
        req.getRequestDispatcher("/user_list.jsp").forward(req,resp);
    }
}
