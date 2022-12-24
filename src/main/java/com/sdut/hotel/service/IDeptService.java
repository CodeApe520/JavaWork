package com.sdut.hotel.service;

import com.sdut.hotel.pojo.Dept;
import com.sdut.hotel.pojo.query.DeptQuery;
import com.sdut.hotel.utils.LayUITableResult;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Dept: jiruichang
//Date: 2022/12/21
//Time: 16:51
public interface IDeptService {
    List<Dept> selectAll();
    LayUITableResult selectByPage(DeptQuery deptQuery);
    Boolean deleteById(Integer id);
    Boolean deleteAll(String[] array);
    Boolean add(Dept dept);
    Dept selectById(int id);
    Boolean update(Dept dept);
}
