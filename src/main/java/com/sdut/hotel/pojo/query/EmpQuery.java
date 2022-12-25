package com.sdut.hotel.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/23
//Time: 10:40
@Data
@AllArgsConstructor
public class EmpQuery {
    private Integer  page ;
    private Integer  limit ;
    private String  name ;
    private String  loc ;
    private String  phone ;
}
