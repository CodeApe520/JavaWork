package com.sdut.hotel.dao.impl;

import com.sdut.hotel.dao.IUserDao;
import com.sdut.hotel.pojo.User;
import com.sdut.hotel.pojo.query.UserQuery;
import com.sdut.hotel.utils.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    public List<User> selectByPage(UserQuery userQuery) {
        String sql = "select id,name,type,password,email,phone,status,gmt_create,gmt_modified from user ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(userQuery.getName())){
            where += "and name like ?";
            args.add("%"+userQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(userQuery.getEmail())){
            where += "and email=?";
            args.add(userQuery.getEmail());
        }
        if (!StringUtils.isEmpty(userQuery.getPhone())){
            where += "and phone=?";
            args.add(userQuery.getPhone());
        }
        if(userQuery.getBeginDate() != null && userQuery.getEndDate() != null){
            where += "and gmt_create between ? and ? ";
            args.add(userQuery.getBeginDate());
            args.add(userQuery.getEndDate());
        }

        String  limit = "";
        if (userQuery != null){
            int offset =(userQuery.getPage() - 1) * userQuery.getLimit();
            limit = "order by id desc limit "+ offset + ","+ userQuery.getLimit();
        }
        List<User> list = jdbcTemplate.query(sql + where + limit, new BeanPropertyRowMapper<User>(User.class),args.toArray());
        return list;
    }

    @Override
    public Long selectTotalCount(UserQuery userQuery) {
        //三个搜索条件 应该有值就拼接，没有值不拼接
        String sql = "select count(*) from user ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(userQuery.getName())){
            where += "and name like ?";
            args.add("%"+userQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(userQuery.getEmail())){
            where += "and email=?";
            args.add(userQuery.getEmail());
        }
        if (!StringUtils.isEmpty(userQuery.getName())){
            where += "and phone=?";
            args.add(userQuery.getPhone());
        }

        Long totalCount = jdbcTemplate.queryForObject(sql+where, Long.class,args.toArray());
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
        String sql = "insert into user(name,password,email,phone,avatar,type) values(?,?,?,?,?,?)";
        int count = jdbcTemplate.update(sql, user.getName(),user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar(),user.getType());
        return count;
    }

    @Override
    public User selectById(int id) {
        String sql = "select id,`name`,password,email,phone from user where id=?";
        //User user = jdbcTemplate.queryForObject(sql, User.class, id);
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
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
        String sql = "select id,name,password,email,phone,type from user where name=? and password =?";
//        User user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),name,password);
//        return user;
        List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class),name,password);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }
}
