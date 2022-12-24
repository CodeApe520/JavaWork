package com.sdut.hotel.dao.impl;

import com.sdut.hotel.dao.IEmpDao;
import com.sdut.hotel.pojo.Emp;
import com.sdut.hotel.pojo.query.EmpQuery;
import com.sdut.hotel.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//Create by IntelliJ IDEA.
//Have a good day!
//Emp: jiruichang
//Date: 2022/12/21
//Time: 17:02
public class EmpDaoImpl implements IEmpDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Emp> selectAll() {
        String sql = "select id,name,dept_id,email,phone from emp";
        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
        return list;
    }

    @Override
    public List<Emp> selectByPage(EmpQuery empQuery) {
        String sql = "select id,name,dept_id,email,phone from emp ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(empQuery.getName())){
            where += "and name like ?";
            args.add("%"+empQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(empQuery.getEmail())){
            where += "and email=?";
            args.add(empQuery.getEmail());
        }
        if (!StringUtils.isEmpty(empQuery.getPhone())){
            where += "and phone=?";
            args.add(empQuery.getPhone());
        }

        String  limit = "";
        if (empQuery != null){
            int offset =(empQuery.getPage() - 1) * empQuery.getLimit();
            limit = "order by id desc limit "+ offset + ","+ empQuery.getLimit();
        }
        List<Emp> list = jdbcTemplate.query(sql + where + limit, new BeanPropertyRowMapper<Emp>(Emp.class),args.toArray());
        return list;
    }

    @Override
    public Long selectTotalCount(EmpQuery empQuery) {
        //三个搜索条件 应该有值就拼接，没有值不拼接
        String sql = "select count(*) from emp ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(empQuery.getName())){
            where += "and name like ?";
            args.add("%"+empQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(empQuery.getEmail())){
            where += "and email=?";
            args.add(empQuery.getEmail());
        }
        if (!StringUtils.isEmpty(empQuery.getName())){
            where += "and phone=?";
            args.add(empQuery.getPhone());
        }

        Long totalCount = jdbcTemplate.queryForObject(sql+where, Long.class,args.toArray());
        return totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "delete from emp where id=?";
        int count = jdbcTemplate.update(sql, id);
        return count;
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        //delete from emp where id in(?,?)
        String sql = "delete from emp where id in(";
        //sql += Stream.of(ids).map(item -> "?").collect(Collectors.joining(","));
        for (Integer id: ids) {
            sql += "?,";
        }
        //delete from emp where id in(?,?,
        sql = sql.substring(0, sql.length() - 1);
        sql +=")";
        int count = jdbcTemplate.update(sql,ids);
        return count;
    }

    @Override
    public Integer add(Emp emp) {
        System.out.println("EmpDaoImpl.add");
        String sql = "insert into emp(name,dept_id,email,phone) values(?,?,?,?)";
        int count = jdbcTemplate.update(sql, emp.getName(),emp.getDepId(),emp.getEmail(),emp.getPhone());
        return count;
    }

    @Override
    public Emp selectById(int id) {
        String sql = "select id,`name`,dept_id,email,phone from emp where id=?";
        //Emp emp = jdbcTemplate.queryForObject(sql, Emp.class, id);
        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class), id);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer update(Emp emp) {
        String sql = "update emp set name=?,dept_id=?,email=?,phone=? where id=?";
        int count = jdbcTemplate.update(sql, emp.getName(),emp.getDepId(),emp.getEmail(),emp.getPhone(),emp.getId());
        return count;    }

}
