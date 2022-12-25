package com.sdut.hotel.dao.impl;

import com.sdut.hotel.dao.IEmpDao;
import com.sdut.hotel.dao.vo.EmpDeptVO;
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
        String sql = "select id,name,dept_id,loc,obj from emp";
        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
        return list;
    }

    @Override
    public List<EmpDeptVO> selectByPage(EmpQuery empQuery) {
        String sql = "SELECT e.id,e.name,e.loc,e.obj,d.id as deptId, d.name as deptName\n" +
                "from emp As e INNER JOIN dept AS d \n" +
                "on e.dept_id=d.id ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(empQuery.getName())){
            where += "and e.name like BINARY ?";
            args.add("%"+empQuery.getName() +"%");
        }
        if (!StringUtils.isEmpty(empQuery.getLoc())){
            where += "and loc=?";
            args.add(empQuery.getLoc());
        }
        if (!StringUtils.isEmpty(empQuery.getObj())){
            where += "and obj=?";
            args.add(empQuery.getObj());
        }

        String  limit = "";
        if (empQuery != null){
            int offset =(empQuery.getPage() - 1) * empQuery.getLimit();
            limit = "order by id desc limit "+ offset + ","+ empQuery.getLimit();
        }
        List<EmpDeptVO> list = jdbcTemplate.query(sql + where + limit, new BeanPropertyRowMapper<EmpDeptVO>(EmpDeptVO.class),args.toArray());
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
        if (!StringUtils.isEmpty(empQuery.getLoc())){
            where += "and loc=?";
            args.add(empQuery.getLoc());
        }
        if (!StringUtils.isEmpty(empQuery.getName())){
            where += "and obj=?";
            args.add(empQuery.getObj());
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
        String sql = "insert into emp(name,dept_id,loc,obj) values(?,?,?,?)";
        int count = jdbcTemplate.update(sql, emp.getName(),emp.getDeptId(),emp.getLoc(),emp.getObj());
        return count;
    }

    @Override
    public Emp selectById(int id) {
        String sql = "select id,`name`,dept_id,loc,obj from emp where id=?";
        //Emp emp = jdbcTemplate.queryForObject(sql, Emp.class, id);
        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class), id);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer update(Emp emp) {
        String sql = "update emp set name=?,dept_id=?,loc=?,obj=? where id=?";
        int count = jdbcTemplate.update(sql, emp.getName(),emp.getDeptId(),emp.getLoc(),emp.getObj(),emp.getId());
        return count;    }

}
