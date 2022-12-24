package com.sdut.hotel.dao.impl;

import com.sdut.hotel.dao.IDeptDao;
import com.sdut.hotel.pojo.Dept;
import com.sdut.hotel.pojo.query.DeptQuery;
import com.sdut.hotel.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Dept: jiruichang
//Date: 2022/12/21
//Time: 17:02
public class DeptDaoImpl implements IDeptDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Dept> selectAll() {
        String sql = "select id,name,addr from dept";
        List<Dept> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Dept>(Dept.class));
        return list;
    }

    @Override
    public List<Dept> selectByPage(DeptQuery deptQuery) {
        System.out.println("DeptDaoImpl.selectByPage");
        String sql = "select id,name,addr from dept ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(deptQuery.getName())){
            where += "and name like ?";
            args.add("%"+deptQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(deptQuery.getAddr())){
            where += "and addr=?";
            args.add(deptQuery.getAddr());
        }


        String  limit = "";
        if (deptQuery != null){
            int offset =(deptQuery.getPage() - 1) * deptQuery.getLimit();
            limit = "order by id desc limit "+ offset + ","+ deptQuery.getLimit();
        }
        List<Dept> list = jdbcTemplate.query(sql + where + limit, new BeanPropertyRowMapper<Dept>(Dept.class),args.toArray());
        return list;
    }

    @Override
    public Long selectTotalCount(DeptQuery deptQuery) {
        //三个搜索条件 应该有值就拼接，没有值不拼接
        String sql = "select count(*) from dept ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(deptQuery.getName())){
            where += "and name like ?";
            args.add("%"+deptQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(deptQuery.getAddr())){
            where += "and addr=?";
            args.add(deptQuery.getAddr());
        }

        Long totalCount = jdbcTemplate.queryForObject(sql+where, Long.class,args.toArray());
        return totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "delete from dept where id=?";
        int count = jdbcTemplate.update(sql, id);
        return count;
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        //delete from dept where id in(?,?)
        String sql = "delete from dept where id in(";
        //sql += Stream.of(ids).map(item -> "?").collect(Collectors.joining(","));
        for (Integer id: ids) {
            sql += "?,";
        }
        //delete from dept where id in(?,?,
        sql = sql.substring(0, sql.length() - 1);
        sql +=")";
        int count = jdbcTemplate.update(sql,ids);
        return count;
    }

    @Override
    public Integer add(Dept dept) {
        System.out.println("DeptDaoImpl.add");
        String sql = "insert into dept(name,addr) values(?,?)";
        int count = jdbcTemplate.update(sql, dept.getName(),dept.getAddr());
        return count;
    }

    @Override
    public Dept selectById(int id) {
        String sql = "select id,`name`,addr from dept where id=?";
        //Dept dept = jdbcTdeptlate.queryForObject(sql, Dept.class, id);
        List<Dept> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dept.class), id);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer update(Dept dept) {
        String sql = "update dept set name=?,addr=? where id=?";
        int count = jdbcTemplate.update(sql, dept.getName(),dept.getAddr(),dept.getId());
        return count;    }

}
