package org.example;

import java.sql.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.util.*;

public class JdbcUtils {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Password7533";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static Connection connection;
    private static PreparedStatement pstmt;
    private static ResultSet resultSet;

    public JdbcUtils() throws Exception{
        Class.forName(DRIVER);
        System.out.println("DB connected");
        connection.close();
    }

    public static void SignUp(String Name,String ID,String Password,int age,String gender) throws Exception{
        try {
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            String sql ="insert into `mytable1` (`username`,`ID`,`Password`,`age`,`gender`) values(?, ?, ?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(Name);
            params.add(ID);
            params.add(Password);
            params.add(age);
            params.add(gender);

            pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1, params.get(0));
            pstmt.setObject(2, params.get(1));
            pstmt.setObject(3, params.get(2));
            pstmt.setObject(4, params.get(3));
            pstmt.setObject(5, params.get(4));
            pstmt.executeUpdate();

            System.out.print("Success");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean LogIn(String id, String password){
        boolean isCorrect = false;
        try{
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            String sql = "select * from `mytable1` where username = ? and Password = ?";
            List<Object> params = new ArrayList<>();
            params.add(id);
            params.add(password);

            pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1,params.get(0));
            pstmt.setObject(2,params.get(1));
            resultSet = pstmt.executeQuery();
            System.out.println("Connected database successfully!!");
            int check = 0;
            //遍历，查找完所有列之后返回false
            while(resultSet.next()){
                check ++;
                System.out.println("id: "+ resultSet.getString("id"));
            }
            if(check == 0){
                System.out.println("Incorrect!!");
            }else{
                System.out.println("Success!!");
                isCorrect = true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return isCorrect;
    }

    public static void History(String id,String Message){
        try{
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
//            Statement statement = connection.createStatement();
//            statement.execute("create table history (Name varchar(50),LocalHistory varchar(50))");
//            System.out.println("create!!!");

            String sql ="insert into `history` (`Name`,`LocalHistory`) values(?,?)";
            List<Object> params02 = new ArrayList<>();
            params02.add(id);
            params02.add(Message);
            pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1, params02.get(0));
            pstmt.setObject(2, params02.get(1));
            pstmt.executeUpdate();
            System.out.println("add!!!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static String getHistory(){
        String History = "";
        try {
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            String sql = "select * from `history`";
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String ID = rs.getString("Name");
                String LocalHistory = rs.getString("LocalHistory");
                History = History + ID + ": " + LocalHistory + "\n";
            }
            rs.close();
            connection.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return History;
    }
}
