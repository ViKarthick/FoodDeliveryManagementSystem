package dbmsproject.dbmsproject;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuFrame extends JFrame {

    private Connection con;
    private JPanel inputPanel;
    private JTextField menuIdField, quantityField, priceField, restIdField, foodNameField;
    private JButton addButton;
    String restaurant_id;
    String foodName;

    public MenuFrame(String restaurant_id, String foodName) {
        this.restaurant_id = restaurant_id;
        this.foodName = foodName;
        initComponents();
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@LAPTOP-9M301MNP:1522:orcl",
                    "scott",
                    "boomthakkali"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to connect to database: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void initComponents() {
        setTitle("Add Menu Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        inputPanel = new JPanel();

        menuIdField = new JTextField(5);
        quantityField = new JTextField(5);
        priceField = new JTextField(5);
        restIdField = new JTextField(10);
        restIdField.setText(restaurant_id);
        restIdField.setEditable(false);
        foodNameField = new JTextField(10);
        foodNameField.setText(foodName);
        foodNameField.setEditable(false);
        addButton = new JButton("Add Menu Item");

        inputPanel.add(new JLabel("Restaurant ID:"));
        inputPanel.add(restIdField);
        inputPanel.add(new JLabel("Food Name:"));
        inputPanel.add(foodNameField);
        inputPanel.add(new JLabel("Menu ID:"));
        inputPanel.add(menuIdField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMenuItem();
            }
        });

        add(inputPanel, BorderLayout.CENTER);
    }

    private void addMenuItem() {
        String menuId = menuIdField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();

        if (menuId.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }

        try {
            // Check if the menu_id is unique
            String checkQuery = "SELECT COUNT(*) FROM menu WHERE menu_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, menuId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Menu ID already exists. Please enter a unique Menu ID.");
                return;
            }

            // Insert into menu table
            String insertMenuQuery = "INSERT INTO menu (menu_id, restaurant_id, food_name) VALUES (?, ?, ?)";
            PreparedStatement insertMenuStmt = con.prepareStatement(insertMenuQuery);
            insertMenuStmt.setString(1, menuId);
            insertMenuStmt.setString(2, restaurant_id);
            insertMenuStmt.setString(3, foodName);
            insertMenuStmt.executeUpdate();

            // Insert into menu_table
            String insertMenuTableQuery = "INSERT INTO menu_table (restaurant_id, food_name, price, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement insertMenuTableStmt = con.prepareStatement(insertMenuTableQuery);
            insertMenuTableStmt.setString(1, restaurant_id);
            insertMenuTableStmt.setString(2, foodName);
            insertMenuTableStmt.setString(3, price);
            insertMenuTableStmt.setString(4, quantity);
            insertMenuTableStmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Menu item added successfully!");
            RestaurantScreen obj = new RestaurantScreen(restaurant_id);
            obj.setVisible(true);
            this.setVisible(false);
            menuIdField.setText("");
            quantityField.setText("");
            priceField.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error adding menu item: " + ex.getMessage());
        }
    }

    public static void main(String restaurant_id, String foodName) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuFrame(restaurant_id, foodName).setVisible(true);
            }
        });
    }
}