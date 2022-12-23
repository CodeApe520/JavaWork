package com.sdut.hotel.service;

import com.sdut.hotel.pojo.Emp;
import com.sdut.hotel.pojo.query.EmpQuery;
import com.sdut.hotel.utils.LayUITableResult;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Emp: jiruichang
//Date: 2022/12/21
//Time: 16:51
public interface IEmpService {
    List<Emp> selectAll();
    LayUITableResult selectByPage(EmpQuery empQuery);
    Boolean deleteById(Integer id);
    Boolean deleteAll(String[] array);
    Boolean add(Emp emp);
    Emp selectById(int id);
    Boolean update(Emp emp);
}
