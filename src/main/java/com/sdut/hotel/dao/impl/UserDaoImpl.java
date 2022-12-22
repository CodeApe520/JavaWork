package com.sdut.hotel.dao.impl;

import com.sdut.hotel.dao.IUserDao;
import com.sdut.hotel.pojo.User;
import com.sdut.hotel.service.IUserService;
import com.sdut.hotel.utils.JDBCUtil;
import com.sdut.hotel.utils.JSONResult;
import com.sdut.hotel.utils.JSONUtil;
import com.sdut.hotel.utils.LayUITableResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/21
//Time: 17:02
public class UserDaoImpl implements IUserDao {
    @Override
    public List<User> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select id,name,password,email,phone from user";
            System.out.println(statement);
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setPhone(phone);


                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection,statement,resultSet);
        }
        return list;
    }

    @Override
    public List<User> selectByPage(Integer offset, Integer limit) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select id,name,password,email,phone from user limit ?,?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, offset);
            statement.setInt(2,limit);
            System.out.println(statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setPhone(phone);


                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection,statement,resultSet);
        }

        return list;
    }

    @Override
    public Long selectTotalCount() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) from user";
            statement = connection.prepareStatement(sql);
            System.out.println(statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection,statement,resultSet);
        }
        return (long)totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        Connection connection = null;
        PreparedStatement statement =null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from user where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            System.out.println(statement);
            count = statement.executeUpdate();
            System.out.println("count: ="+count);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection,statement,null);
        }
        return count;
    }
}
