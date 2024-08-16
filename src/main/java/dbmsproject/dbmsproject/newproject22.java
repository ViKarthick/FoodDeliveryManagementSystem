package dbmsproject.dbmsproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class newproject22 extends javax.swing.JFrame {
    private Connection con;

    // Components for UI
    private javax.swing.JLabel food_name_label;
    private javax.swing.JTextField food_name_enter;
    private javax.swing.JLabel category_label;
    private javax.swing.JTextField category_enter;
    private javax.swing.JButton add_menu_item;
    String restaurant_id;

    public newproject22(String restaurant_id) {
        this.restaurant_id = restaurant_id;
        initComponents();
        connectToDatabase();
    }

    private void initComponents() {
        food_name_label = new javax.swing.JLabel();
        food_name_enter = new javax.swing.JTextField();
        category_label = new javax.swing.JLabel();
        category_enter = new javax.swing.JTextField();
        add_menu_item = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add New Menu Item");

        food_name_label.setText("Food Name");

        category_label.setText("Category");

        add_menu_item.setText("Add Menu Item");
        add_menu_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_menu_itemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(category_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(category_enter, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(food_name_label)
                        .addGap(18, 18, 18)
                        .addComponent(food_name_enter, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(add_menu_item)
                .addGap(148, 148, 148))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(food_name_label)
                    .addComponent(food_name_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category_label)
                    .addComponent(category_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(add_menu_item)
                .addGap(49, 49, 49))
        );

        pack();
    }

    private void add_menu_itemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String foodName = food_name_enter.getText();
            String category = category_enter.getText();

            // Check if the food_name and restaurant_id combo exists in menu table
            String checkComboQuery = "SELECT COUNT(*) FROM menu WHERE restaurant_id = ? AND food_name = ?";
            PreparedStatement psComboCheck = con.prepareStatement(checkComboQuery);
            psComboCheck.setString(1, restaurant_id);
            psComboCheck.setString(2, foodName);
            ResultSet rsComboCheck = psComboCheck.executeQuery();
            if (rsComboCheck.next() && rsComboCheck.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "The food name and restaurant ID combination already exists.");
                RestaurantScreen obj = new RestaurantScreen(restaurant_id);
                obj.setVisible(true);
                this.setVisible(false);
                return;
            }

            // Check if the food name is unique in FOOD_CATEGORY table
            String checkQuery = "SELECT food_name FROM FOOD_CATEGORY WHERE food_name = ?";
            PreparedStatement psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, foodName);
            ResultSet rsCheck = psCheck.executeQuery();
            if (rsCheck.next()) {
                JOptionPane.showMessageDialog(this, "Food name already exists.");
                MenuFrame obj = new MenuFrame(restaurant_id, foodName);
                obj.setVisible(true);
                this.setVisible(false);
                return;
            }

            // Insert into FOOD_CATEGORY table
            String insertCategoryQuery = "INSERT INTO FOOD_CATEGORY (food_name, category) VALUES (?, ?)";
            PreparedStatement psCategory = con.prepareStatement(insertCategoryQuery);
            psCategory.setString(1, foodName);
            psCategory.setString(2, category);
            int rowsInsertedCategory = psCategory.executeUpdate();

            if (rowsInsertedCategory > 0) {
                JOptionPane.showMessageDialog(this, "New menu item category added successfully!");
                // Open the menu frame here after successful insertion
                MenuFrame obj = new MenuFrame(restaurant_id, foodName);
                obj.setVisible(true);
                this.dispose(); // Close current frame
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add menu item category. Please try again.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(newproject22.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String restaurant_id) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newproject22(restaurant_id).setVisible(true);
            }
        });
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
}