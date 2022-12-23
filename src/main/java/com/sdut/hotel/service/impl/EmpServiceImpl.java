package com.sdut.hotel.service.impl;

import com.sdut.hotel.dao.IEmpDao;
import com.sdut.hotel.dao.impl.EmpDaoImpl;
import com.sdut.hotel.pojo.Emp;
import com.sdut.hotel.pojo.query.EmpQuery;
import com.sdut.hotel.service.IEmpService;
import com.sdut.hotel.utils.LayUITableResult;
import com.sdut.hotel.utils.MD5Util;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Emp: jiruichang
//Date: 2022/12/21
//Time: 16:54
public class EmpServiceImpl implements IEmpService {
    private IEmpDao empDao = new EmpDaoImpl();

    @Override
    public List<Emp> selectAll() {
        return empDao.selectAll();
    }

    @Override
    public LayUITableResult selectByPage(EmpQuery empQuery) {
        //查询当前页的数量
        List<Emp> list = empDao.selectByPage(empQuery);

        //查询总的数量
        Long totalCount = empDao.selectTotalCount(empQuery);
        return LayUITableResult.ok(list, totalCount);
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer count = empDao.deleteById(id);
        return count == 1 ? true : false;
    }

    @Override
    public Boolean deleteAll(String[] array) {
        Integer[] ids = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ids[i] = Integer.parseInt(array[i]);
        }

        int count = empDao.deleteAll(ids);
        return count == array.length;
    }

    @Override
    public Boolean add(Emp emp) {
        System.out.println("EmpServiceImpl.add");
        int count = empDao.add(emp);
        return count == 1;
    }

    @Override
    public Emp selectById(int id) {
        return empDao.selectById(id);
    }

    @Override
    public Boolean update(Emp emp) {
        int count = empDao.update(emp);
        return count == 1;
    }

}
