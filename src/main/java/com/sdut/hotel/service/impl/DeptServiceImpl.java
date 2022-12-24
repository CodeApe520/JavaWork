package com.sdut.hotel.service.impl;

import com.sdut.hotel.dao.IDeptDao;
import com.sdut.hotel.dao.impl.DeptDaoImpl;
import com.sdut.hotel.dao.vo.DeptCountVO;
import com.sdut.hotel.pojo.Dept;
import com.sdut.hotel.pojo.query.DeptQuery;
import com.sdut.hotel.service.IDeptService;
import com.sdut.hotel.utils.JSONResult;
import com.sdut.hotel.utils.LayUITableResult;

import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Dept: jiruichang
//Date: 2022/12/21
//Time: 16:54
public class DeptServiceImpl implements IDeptService {
    private IDeptDao deptDao = new DeptDaoImpl();

    @Override
    public List<Dept> selectAll() {
        return deptDao.selectAll();
    }

    @Override
    public LayUITableResult selectByPage(DeptQuery deptQuery) {
        //查询当前页的数量
        List<Dept> list = deptDao.selectByPage(deptQuery);

        //查询总的数量
        Long totalCount = deptDao.selectTotalCount(deptQuery);
        return LayUITableResult.ok(list, totalCount);
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer count = deptDao.deleteById(id);
        return count == 1 ? true : false;
    }

    @Override
    public Boolean deleteAll(String[] array) {
        Integer[] ids = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ids[i] = Integer.parseInt(array[i]);
        }

        int count = deptDao.deleteAll(ids);
        return count == array.length;
    }

    @Override
    public Boolean add(Dept dept) {
        System.out.println("DeptServiceImpl.add");
        int count = deptDao.add(dept);
        return count == 1;
    }

    @Override
    public Dept selectById(int id) {
        return deptDao.selectById(id);
    }

    @Override
    public Boolean update(Dept dept) {
        int count = deptDao.update(dept);
        return count == 1;
    }

    @Override
    public JSONResult selectDeptCount() {
        List<DeptCountVO> list =deptDao.selectDeptCount();
        return JSONResult.ok(list);
    }

}
