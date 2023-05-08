import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.tree.ExpandVetoException;
public class Main 
{
    public static void main(String[] args)
    {
        // try
        // {
        //     Class.forName("org.mariadb.jdbc.Driver");
        // }
        // catch(Exception e)
        // {
        //     System.out.println("Error: " + e.getMessage());
        // }
        // String url = "jdbc:mariadb://localhost:3306/u22744968_sakila";
        // String username = "root";
        // String password = "Timone2357";
        // try
        // {
        //     Connection con = DriverManager.getConnection(url, username, password);
        //     Statement stmt = con.createStatement();
        //     ResultSet rs = stmt.executeQuery("SELECT * FROM actor");
        //     while(rs.next())
        //     {
        //         System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        //     }
        // }
        // catch(Exception e)
        // {
        //     System.out.println("Error: " + e.getMessage());
        // }
        JFrame homePage = new HomePage("Sakila");
        homePage.setVisible(true);
    }
}