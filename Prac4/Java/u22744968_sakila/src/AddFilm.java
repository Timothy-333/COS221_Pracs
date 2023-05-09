import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.JPopupMenu.Separator;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.xml.transform.Result;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

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
    private Connection conn;
    HomePage homePage;
    public AddFilm(String title, Connection conn, HomePage homePage)
    {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.conn = conn;
        this.homePage = homePage;
        setPlaceholderText(titleField, "Title...");
        setPlaceholderText(descriptionField, "Description...");
        setPlaceholderText(releaseYearField, "Release Year...");
        setPlaceholderText(lengthField, "Length (minutes)...)");
        setPlaceholderText(rentalDurationField, "Rental Duration (days)...");
        setPlaceholderText(rentalCostField, "Rental Cost (per day)...)");
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
        ratingBox.addItem("G");
        ratingBox.addItem("PG");
        ratingBox.addItem("PG-13");
        ratingBox.addItem("R");
        ratingBox.addItem("NC-17");
        submitButton.addActionListener(e -> submit());
        backButton.addActionListener(e -> this.dispose());
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
    private void submit()
    {
        try 
        {
            String title = titleField.getText();
            String description = descriptionField.getText();
            int releaseYear = Integer.parseInt(releaseYearField.getText());
            int languageId = languageBox.getSelectedIndex() + 1;
            int originalLanguageId = orginalLanguageBox.getSelectedIndex() + 1;
            int rentalDuration = Integer.parseInt(rentalDurationField.getText());
            double rentalRate = Double.parseDouble(rentalCostField.getText());
            int length = Integer.parseInt(lengthField.getText());
            double replacementCost = Double.parseDouble(replacementCostField.getText());
            String rating = ratingBox.getSelectedItem().toString();
            String query = "INSERT INTO film (title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String specialFeatures = "";
            if (trailersCheck.isSelected()) {
                specialFeatures += "Trailers,";
            }
            if (deletedCheck.isSelected()) {
                specialFeatures += "Deleted Scenes,";
            }
            if (commentariesCheck.isSelected()) {
                specialFeatures += "Commentaries,";
            }
            if (bndScenesCheck.isSelected()) {
                specialFeatures += "Behind the Scenes,";
            }
            // remove the trailing comma
            if (!specialFeatures.isEmpty()) {
                specialFeatures = specialFeatures.substring(0, specialFeatures.length() - 1);
            }
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, releaseYear);
            pstmt.setInt(4, languageId);
            pstmt.setInt(5, originalLanguageId);
            pstmt.setInt(6, rentalDuration);
            pstmt.setDouble(7, rentalRate);
            pstmt.setInt(8, length);
            pstmt.setDouble(9, replacementCost);
            pstmt.setString(10, rating);
            pstmt.setString(11, specialFeatures);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(homePage, "Film Added", "Success", JOptionPane.INFORMATION_MESSAGE);
            homePage.populateFilms();
            this.dispose();
        } 
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Error Adding Film", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

