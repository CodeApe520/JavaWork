package com.sdut.hotel.controller;

import com.sdut.hotel.pojo.Emp;
import com.sdut.hotel.pojo.query.EmpQuery;
import com.sdut.hotel.service.IEmpService;
import com.sdut.hotel.service.impl.EmpServiceImpl;
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
//Emp: jiruichang
//Date: 2022/12/20
//Time: 10:47
@WebServlet("/emp")
public class EmpServlet extends HttpServlet {

    private IEmpService empService = new EmpServiceImpl();

    //http://localhost:8081/hotel/?method
    //http://localhost:8081/hotel/?deleteById&id=1
    // /hotel/emp?method=selectByPage&page=1&limit=10&name=&email=&phone=
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
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
            case "deleteAll":
                deleteAll(req,resp);
                break;
            case "add":
                add(req,resp);
                break;
            case "getEmpUpdatePage":
                getEmpUpdatePage(req,resp);
                break;
            case "update":
                update(req,resp);
                break;


        }


    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.update");
        String id = req.getParameter("id");
        System.out.println("EmpServlet.add");
        String name = req.getParameter("name");
        String deptId = req.getParameter("deptId");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        Emp emp = new Emp();
        emp.setId(Integer.parseInt(id));
        emp.setName(name);
        emp.setDeptId(Integer.parseInt(deptId));
        emp.setEmail(email);
        emp.setPhone(phone);

        boolean isSuccess = empService.update(emp);
        if (isSuccess){
            JSONUtil.obj2Json(JSONResult.ok("修改成功"),resp);
        }else {
            JSONUtil.obj2Json(JSONResult.error("修改失败"),resp);
        }
    }

    private void getEmpUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EmpServlet.getEmpUpdatePage");
        String id = req.getParameter("id");
        Emp emp = empService.selectById(Integer.parseInt(id));
        req.setAttribute("emp",emp);
        req.getRequestDispatcher("/emp/emp_update.jsp").forward(req,resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.add");
        String name = req.getParameter("name");
        String deptId = req.getParameter("deptId");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        Emp emp = new Emp();
        emp.setName(name);
        emp.setDeptId(Integer.parseInt(deptId));
        emp.setEmail(email);
        emp.setPhone(phone);

        boolean isSuccess = empService.add(emp);
        if (isSuccess){
            JSONUtil.obj2Json(JSONResult.ok("添加成功"),resp);
        }else {
            JSONUtil.obj2Json(JSONResult.error("添加失败"),resp);
        }
    }

    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.selectByAll");
        //"14，15"
        String ids = req.getParameter("ids");
        String[] array = ids.split(",");
        boolean isSuccess = empService.deleteAll(array);

        if (isSuccess){
            JSONUtil.obj2Json(JSONResult.ok("删除成功"),resp);
        }else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"),resp);
        }
    }

    //https://www.layuiweb.com/demo/table/emp/-page=1&limit=30.js?page=1&limit=10
    ///emp?method=selectById&page=1&limit=10
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.selectByPage");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        EmpQuery empQuery = new EmpQuery(page,limit,name,email,phone);

        LayUITableResult layUITableResult = empService.selectByPage(empQuery);
        JSONUtil.obj2Json(layUITableResult,resp);

    }



    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("EmpServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = empService.deleteById(Integer.parseInt(id));
        //删除完了之后应该重定向查找所有界面  /hotel/emp?method=selectAll
//        resp.sendRedirect(req.getContextPath() + "/emp?method=selectAll");
        if (isSuccess){
            JSONUtil.obj2Json(JSONResult.ok("删除成功"),resp);
        }else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"),resp);
        }



    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EmpServlet.selectAll");
        List<Emp> list = empService.selectAll();
        // 把数据放到req这个域对象
        req.setAttribute("list",list);
        // 转发到emp_list.jsp页面进行展示
        req.getRequestDispatcher("/emp_list.jsp").forward(req,resp);
    }
}
