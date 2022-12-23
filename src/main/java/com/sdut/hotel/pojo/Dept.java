package com.sdut.hotel.pojo;

import lombok.Data;

import java.util.Date;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/23
//Time: 16:52
@Data
public class Dept {
    private Integer id;
    private String name;
    private String addr;
    private Integer deleted;
    private Date gmtCreate;
    private Date gmtModified;
}
