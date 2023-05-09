import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class HomePage extends JFrame 
{
    private JPanel mainPanel;
    private JTabbedPane TabsPane;
    private JTextField searchTextField;
    private JTextArea welcomeToSakilaTextArea;
    private JTable staffTable;
    private JScrollPane staffTableScroll;
    private JButton addFilmButton;
    private JTable filmsTable;
    private JScrollPane filmsTableScroll;
    Connection conn = null;
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
                        System.out.println("Report");
                        break;
                    case 4:
                        System.out.println("Notifications");
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
            searchTextField.addKeyListener(new KeyAdapter() 
            {
                @Override
                public void keyReleased(KeyEvent e) 
                {
                    filterTable(searchTextField.getText());
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
        });
    }
    private void populateFilms()
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
    private ResultSet selectQuery(String query)
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
    public static void main(String[] args)
    {
        JFrame homePage = new HomePage("Sakila");
        homePage.setVisible(true);
    }
}
