import javax.swing.*;
import javax.xml.transform.Result;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class AddFilm extends JFrame 
{

    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField releaseYearField;
    private JComboBox languageBox;
    private JComboBox orginalLanguageBox;
    private JTextField lengthField;
    private JTextField rentalDurationField;
    private JTextField rentalCostField;
    private JTextField replacementCostField;
    private JComboBox ratingBox;
    private JButton submitButton;
    private JButton backButton;
    private JCheckBox trailersCheck;
    private JCheckBox deletedCheck;
    private JCheckBox commentariesCheck;
    private JCheckBox bndScenesCheck;
    private JPanel preferences;
    private JPanel mainPanel;

    public AddFilm(String title, Connection conn, HomePage homePage)
    {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        setPlaceholderText(titleField, "Title...");
        setPlaceholderText(descriptionField, "Description...");
        setPlaceholderText(releaseYearField, "Release Year...");
        setPlaceholderText(lengthField, "Length...");
        setPlaceholderText(rentalDurationField, "Rental Duration...");
        setPlaceholderText(rentalCostField, "Rental Cost...");
        setPlaceholderText(replacementCostField, "Replacement Cost...");
        ResultSet rs= homePage.selectQuery("SELECT name FROM language");
        try
        {
            while(rs.next())
            {
                languageBox.addItem(rs.getString("name"));
                orginalLanguageBox.addItem(rs.getString("name"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        ResultSet rs2= homePage.selectQuery("SELECT rating FROM film_rating");
    }
    private void setPlaceholderText(JTextField field, String text)
    {
        field.setText(text);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusListener() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (field.getText().equals(text)) 
                {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (field.getText().isEmpty()) 
                {
                    field.setForeground(Color.GRAY);
                    field.setText(text);
                }
            }
        });
    }
}
