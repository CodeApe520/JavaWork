package com.sdut.hotel.pojo;

import lombok.Data;

import java.util.Date;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/23
//Time: 16:49
@Data
public class Emp {
    private Integer id;
    private String name;
    private String loc;
    private String phone;
    //所在部门id
    private Integer deptId;
    private Integer deleted;
    private Date gmtCreate;
    private Date gmtModified;
}
