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
        String sql = "select id,name,password,email,phone from user limit ?,?";
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
}
