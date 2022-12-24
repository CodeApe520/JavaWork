package com.sdut.hotel.dao;

import com.sdut.hotel.dao.vo.EmpDeptVO;
import com.sdut.hotel.pojo.Emp;
import com.sdut.hotel.pojo.query.EmpQuery;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Emp: jiruichang
//Date: 2022/12/21
//Time: 16:57
public interface IEmpDao {
    List<Emp> selectAll();
    List<EmpDeptVO> selectByPage(EmpQuery empQuery);
    Long selectTotalCount(EmpQuery empQuery);
    Integer deleteById(Integer id);
    Integer deleteAll(Integer[] ids);
    Integer add(Emp emp);
    Emp selectById(int id);
    Integer update(Emp emp);
}
