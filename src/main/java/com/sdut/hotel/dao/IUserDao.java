package com.sdut.hotel.dao;

import com.sdut.hotel.pojo.User;
import com.sdut.hotel.utils.LayUITableResult;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/21
//Time: 16:57
public interface IUserDao {
    List<User> selectAll();
    List<User> selectByPage(Integer offset, Integer limit);
    Long selectTotalCount();
    Integer deleteById(Integer id);

    Integer deleteAll(Integer[] ids);
}
