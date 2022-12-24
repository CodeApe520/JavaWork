package com.sdut.hotel.dao;

import com.sdut.hotel.pojo.User;
import com.sdut.hotel.pojo.query.UserQuery;
import com.sdut.hotel.utils.LayUITableResult;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/21
//Time: 16:57
public interface IUserDao {
    List<User> selectAll();
    List<User> selectByPage(UserQuery userQuery);
    Long selectTotalCount(UserQuery userQuery);
    Integer deleteById(Integer id);
    Integer deleteAll(Integer[] ids);
    Integer add(User user);
    User selectById(int id);
    Integer update(User user);
    User login(String name, String password);
    Integer updateStatus(String id, Integer status);
}
