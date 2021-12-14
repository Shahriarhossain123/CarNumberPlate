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
       try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql:///car_number","root","");
            return con;
        
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex + ". More Work");
            JOptionPane.showMessageDialog(null,"Database Connection is Error");
        }
        return null;
   }
}
