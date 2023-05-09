import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class HomePage extends JFrame 
{
    private JPanel mainPanel;
    private JTabbedPane TabsPane;
    private JTextField staffSearchTextField;
    private JTextArea welcomeToSakilaTextArea;
    private JTable staffTable;
    private JScrollPane staffTableScroll;
    private JButton addFilmButton;
    private JTable filmsTable;
    private JScrollPane filmsTableScroll;
    private JTable reportTable;
    private JScrollPane reportTableScroll;
    private JButton notificationsListBtn;
    private JTable notificationsTable;
    private JTabbedPane notificationsTabsPane;
    private JButton notificationsCreateSubmitBtn;
    private JButton notificationsDeleteSubmitBtn;
    private JButton notificationsListSubmitBtn;
    private JTable notificationsAllTable;
    private JTable notificationsDroppedTable;
    private JPanel homeJPanel;
    private JPanel staffJPanel;
    private JPanel filmsJPanel;
    private JPanel notificationsJPanel;
    private JPanel reportJPanel;
    private JComboBox ntfCstore_id;
    private JTextField ntfCfname;
    private JTextField ntfClname;
    private JTextField ntfCemail;
    private JComboBox ntfCactive;
    private JTextField ntfCaddress1;
    private JTextField ntfCaddress2;
    private JTextField ntfCdistrict;
    private JComboBox ntfCcityBox;
    private JTextField ntfCpcode;
    private JTextField ntfCphone;
    Connection conn = null;
    AddFilm addFilm;
    public HomePage(String title) 
    {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setContentPane(mainPanel);

        config config = new config();
        String url = config.getDBURL();
        String user = config.getDBUsername();
        String password = config.getDBPassword();

        DefaultTableModel model = new DefaultTableModel();
        staffTable.setModel(model);

        try
        {
            conn = DriverManager.getConnection(url, user, password);
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        TabsPane.addChangeListener(new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent e) 
            {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                switch(index)
                {
                    case 0:
                        System.out.println("Home");
                        break;
                    case 1:
                        staffTab();
                        break;
                    case 2:
                        filmsTab();
                        break;
                    case 3:
                        reportTab();
                        break;
                    case 4:
                        notificationsTab();
                        break;
                    default:
                        System.out.println("Error");
                        break;
                }
            }
        });
    }
    private void staffTab()
    {
        try
        {
            ResultSet rs = selectQuery("SELECT * FROM staff");
            String[] columnNames = {"First Name", "Last Name", "Address", "Address 2", "District", "City", "Postal Code", "Phone", "Store", "Active"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while(rs.next())
            {
                String staffName = rs.getString("first_name");
                String staffLastname = rs.getString("last_name");
                String addressQuery = "SELECT * FROM address WHERE address_id = " + rs.getInt("address_id");
                ResultSet addressRS = selectQuery(addressQuery);
                String address = "";
                String address2 = "";
                String district = "";
                String city = "";
                String postalCode = "";
                String phone = "";
                if(addressRS != null && addressRS.next())
                {
                    address = addressRS.getString("address");
                    address2 = addressRS.getString("address2");
                    district = addressRS.getString("district");
                    city = addressRS.getString("city_id");
                    postalCode = addressRS.getString("postal_code");
                    phone = addressRS.getString("phone");
                }
                String store = rs.getString("store_id");
                String active = rs.getString("active");
                if(active.equals("1"))
                {
                    active = "Yes";
                }
                else
                {
                    active = "No";
                }
                String row[] = {staffName, staffLastname, address, address2, district, city, postalCode, phone, store, active};
                model.addRow(row);
            }
            staffTable.setModel(model);
            staffSearchTextField.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e) 
                {
                    filterTable(staffSearchTextField.getText());
                }
            });            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void filmsTab() 
    {
        populateFilms();
        addFilmButton.addActionListener(e -> 
        {
            if(addFilm == null)
            {
                addFilm = new AddFilm("Add Film", conn, this);
            }
            addFilm.setVisible(true);
            setEnabled(false);
            addFilm.addWindowListener(new WindowAdapter() 
            {
                @Override
                public void windowClosed(WindowEvent e) 
                {
                    setEnabled(true);
                }
            });
        });
    }
    public void reportTab()
    {
        String[] columnNames = {"Store", "Genre", "Number of Movies"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        try
        {
            ResultSet rs = selectQuery("SELECT i.store_id, c.name, COUNT(*) FROM u22744968_sakila.inventory as i join u22744968_sakila.film_category as f on i.film_id = f.film_id join u22744968_sakila.category as c on f.category_id = c.category_id group by i.store_id, c.name;");
            while(rs.next())
            {
                String store = rs.getString("store_id");
                String genre = rs.getString("name");
                String count = rs.getString("COUNT(*)");
                String[] row = {store, genre, count};
                model.addRow(row);
            }
            reportTable.setModel(model);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void notificationsTab()
    {
        System.out.println("Notifications Tab");
        notificationsTabsPane.addChangeListener(new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent e) 
            {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println(index);
                switch(index)
                {
                    case 0:
                        createClient();
                        break;
                    case 1:
                        updateClient();
                        break;
                    case 2:
                        deleteClient();
                        break;
                    case 3:
                        listClient();
                        break;
                    default:
                        System.out.println("Error");
                        break;
                }
            }
        });
        createClient();
    }
    private void createClient()
    {
        notificationsCreateSubmitBtn.addActionListener(null);

        setPlaceholderText(ntfCfname, "First Name...");
        setPlaceholderText(ntfClname, "Last Name...");
        setPlaceholderText(ntfCemail, "Email...");
        setPlaceholderText(ntfCaddress1, "Address 1...");
        setPlaceholderText(ntfCaddress2, "Address 2...");
        setPlaceholderText(ntfCdistrict, "District...");
        setPlaceholderText(ntfCpcode, "Postal Code...");
        setPlaceholderText(ntfCphone, "Phone...");
        ntfCactive.addItem("Yes");
        ntfCactive.addItem("No");
        ResultSet storeIds = selectQuery("SELECT store_id FROM store");
        try
        {
            while(storeIds.next())
            {
                ntfCstore_id.addItem(storeIds.getString("store_id"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        ResultSet cities = selectQuery("SELECT city FROM city");
        try
        {
            while(cities.next())
            {
                ntfCcityBox.addItem(cities.getString("city"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        notificationsCreateSubmitBtn.addActionListener(e2 -> 
        {
            String fname = ntfCfname.getText();
            String lname = ntfClname.getText();
            String email = ntfCemail.getText();
            String address1 = ntfCaddress1.getText();
            String address2 = ntfCaddress2.getText();
            String district = ntfCdistrict.getText();
            String city = ntfCcityBox.getSelectedItem().toString();
            String pcode = ntfCpcode.getText();
            String phone = ntfCphone.getText();
            String store = ntfCstore_id.getSelectedItem().toString();
            String active = ntfCactive.getSelectedItem().toString();
            if(active.equals("Yes"))
            {
                active = "1";
            }
            else
            {
                active = "0";
            }
            String addressQuery = "INSERT INTO address (address, address2, district, city_id, postal_code, phone) VALUES (?, ?, ?, ?, ?, ?)";
            try
            {
                PreparedStatement addressStmt = conn.prepareStatement(addressQuery);
                addressStmt.setString(1, address1);
                addressStmt.setString(2, address2);
                addressStmt.setString(3, district);
                addressStmt.setInt(4, ntfCcityBox.getSelectedIndex() + 1);
                addressStmt.setString(5, pcode);
                addressStmt.setString(6, phone);
                addressStmt.executeUpdate();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding address!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            String customerQuery = "INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) VALUES (?, ?, ?, ?, (SELECT address_id FROM address WHERE address = ? AND address2 = ? AND district = ? AND city_id = ? AND postal_code = ? AND phone = ?), ?)";
            try 
            {
                PreparedStatement customerStmt = conn.prepareStatement(customerQuery);
                customerStmt.setInt(1, Integer.parseInt(store));
                customerStmt.setString(2, fname);
                customerStmt.setString(3, lname);
                customerStmt.setString(4, email);
                customerStmt.setString(5, address1);
                customerStmt.setString(6, address2);
                customerStmt.setString(7, district);
                customerStmt.setInt(8, ntfCcityBox.getSelectedIndex() + 1);
                customerStmt.setString(9, pcode);
                customerStmt.setString(10, phone);
                customerStmt.setInt(11, Integer.parseInt(active));
                customerStmt.executeUpdate();
                ntfCfname.setText("");
                ntfClname.setText("");
                ntfCemail.setText("");
                ntfCaddress1.setText("");
                ntfCaddress2.setText("");
                ntfCdistrict.setText("");
                ntfCpcode.setText("");
                ntfCphone.setText("");
                ntfCactive.setSelectedIndex(0);
                ntfCcityBox.setSelectedIndex(0);
                ntfCstore_id.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (SQLException ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding customer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void updateClient()
    {
        
    }
    private void deleteClient()
    {

    }
    private void listClient()
    {

    }
    public void populateFilms()
    {
        String[] columnNames = {"ID", "Title", "Description", "Release Year", "Language", "Rental Duration", "Rental Rate", "Length", "Replacement Cost", "Rating", "Special Features"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        try 
        {
            ResultSet rs = selectQuery("SELECT * FROM film");
            while (rs.next())
            {
                String id = rs.getString("film_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String releaseYear = rs.getString("release_year");
                String language = rs.getString("language_id");
                String rentalDuration = rs.getString("rental_duration");
                String rentalRate = rs.getString("rental_rate");
                String length = rs.getString("length");
                String replacementCost = rs.getString("replacement_cost");
                String rating = rs.getString("rating");
                String specialFeatures = rs.getString("special_features");
    
                String[] row = {id, title, description, releaseYear, language, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures};
                model.addRow(row);
            }
            filmsTable.setModel(model);
        }
        catch (Exception ex) 
        {
        }
    }
    public ResultSet selectQuery(String query)
    {
        ResultSet rs = null;
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return rs;
    }
    private void filterTable(String searchStr) 
    {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        staffTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchStr));
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
    public static void main(String[] args)
    {
        JFrame homePage = new HomePage("Sakila");
        homePage.setVisible(true);
    }
}
