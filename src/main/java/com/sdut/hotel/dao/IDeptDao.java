package com.sdut.hotel.dao;

import com.sdut.hotel.dao.vo.DeptCountVO;
import com.sdut.hotel.pojo.Dept;
import com.sdut.hotel.pojo.query.DeptQuery;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Dept: jiruichang
//Date: 2022/12/21
//Time: 16:57
public interface IDeptDao {
    List<Dept> selectAll();
    List<Dept> selectByPage(DeptQuery deptQuery);
    Long selectTotalCount(DeptQuery deptQuery);
    Integer deleteById(Integer id);
    Integer deleteAll(Integer[] ids);
    Integer add(Dept dept);
    Dept selectById(int id);
    Integer update(Dept dept);
    List<DeptCountVO> selectDeptCount();
}
