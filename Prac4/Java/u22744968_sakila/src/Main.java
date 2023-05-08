import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.tree.ExpandVetoException;
public class Main 
{
    public static void main(String[] args)
    {
        JFrame homePage = new HomePage("Sakila");
        homePage.setVisible(true);
    }
}