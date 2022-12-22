package com.sdut.hotel.dao.impl;

import com.sdut.hotel.dao.IUserDao;
import com.sdut.hotel.pojo.User;
import com.sdut.hotel.utils.JDBCUtil;
import com.sdut.hotel.utils.JDBCUtil_Back;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/21
//Time: 17:02
public class UserDaoImpl implements IUserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<User> selectAll() {
        String sql = "select id,name,password,email,phone from user";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return list;
    }

    @Override
    public List<User> selectByPage(Integer offset, Integer limit) {
        String sql = "select id,`name`,password,email,phone from user order by id limit ?,?";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class),offset,limit);
        return list;
    }

    @Override
    public Long selectTotalCount() {
        String sql = "select count(*) from user";
        Long totalCount = jdbcTemplate.queryForObject(sql, Long.class);
        return totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "delete from user where id=?";
        int count = jdbcTemplate.update(sql, id);
        return count;
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        //delete from user where id in(?,?)
        String sql = "delete from user where id in(";
        //sql += Stream.of(ids).map(item -> "?").collect(Collectors.joining(","));
        for (Integer id: ids) {
            sql += "?,";
        }
        //delete from user where id in(?,?,
        sql = sql.substring(0, sql.length() - 1);
        sql +=")";
        int count = jdbcTemplate.update(sql,ids);
        return count;
    }

    @Override
    public Integer add(User user) {
        String sql = "insert into user(name,password,email,phone,avatar) values(?,?,?,?,?)";
        int count = jdbcTemplate.update(sql, user.getName(),user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar());
        return count;
    }

    @Override
    public User selectById(int id) {
        String sql = "select id,`name`,password,email,phone from user where id=?";
        //User user = jdbcTemplate.queryForObject(sql, User.class, id);
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        return list.get(0);
    }

    @Override
    public Integer update(User user) {
        String sql = "update user set name=?,password=?,email=?,phone=?,avatar=? where id=?";
        int count = jdbcTemplate.update(sql, user.getName(),user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar(),user.getId());
        return count;    }

    @Override
    public User login(String name, String password) {
        System.out.println("UserDaoImpl.login");
        String sql = "select id,name,password,email,phone from user where name=? and password =?";
        User user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),name,password);
        return user;
    }
}
