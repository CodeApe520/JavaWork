package com.sdut.hotel.service;

import com.sdut.hotel.pojo.User;
import com.sdut.hotel.pojo.query.UserQuery;
import com.sdut.hotel.utils.LayUITableResult;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/21
//Time: 16:51
public interface IUserService {
    List<User> selectAll();
    LayUITableResult selectByPage(UserQuery userQuery);
    Boolean deleteById(Integer id);
    Boolean deleteAll(String[] array);
    Boolean add(User user);
    User selectById(int id);
    Boolean update(User user);
    User login(String name, String password);
}
