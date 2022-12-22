package com.sdut.hotel.utils;

import java.sql.*;

//Create by IntelliJ IDEA.
//Have a good day!
//User: jiruichang
//Date: 2022/12/20
//Time: 12:22
public class JDBCUtil_Back {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel?useUnicode=true&character=UTF-8","root","root155893");
        return connection;
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
