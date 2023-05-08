import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomePage extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField searchTextField;
    private JTextArea welcomeToSakilaTextArea;
    private JTable table1;

    public HomePage(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setContentPane(mainPanel);

        config config = new config();
        String url = config.getDBURL();
        String user = config.getDBUsername();
        String password = config.getDBPassword();

        // Set up the table model
        DefaultTableModel model = new DefaultTableModel();
        table1.setModel(model);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Set up the query statement
            String sql = "SELECT * FROM staff";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // Populate the table model with the query results
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(rs.getMetaData().getColumnLabel(i));
                }
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) 
                    {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
