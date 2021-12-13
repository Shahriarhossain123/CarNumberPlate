/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.tool;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author shahr
 */
public class JavaConnection {
    Connection conn;
   public static Connection dBConnect()
   {
       try{
           
           Class.forName("com.mysql.jdbc.Driver");  
           Connection conn;
           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_number","root","");
           return conn;
            
           
       }catch(ClassNotFoundException | SQLException ex){
           JOptionPane.showMessageDialog(null,"Database Connection is Error");
       }
        return null;
   }
}
