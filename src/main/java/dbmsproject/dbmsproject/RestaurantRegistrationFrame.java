package dbmsproject.dbmsproject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.sql.Connection;

public class RestaurantRegistrationFrame extends javax.swing.JFrame {
    Connection con;
   

    // Components for UI
    private javax.swing.JLabel rest_id_label;
    private javax.swing.JTextField rest_id_enter;
    private javax.swing.JLabel rest_name_label;
    private javax.swing.JTextField rest_name_enter;
    private javax.swing.JLabel contact_no_label;
    private javax.swing.JTextField contact_no_enter;
    private javax.swing.JLabel rating_label;
    private javax.swing.JTextField rating_enter;
    private javax.swing.JLabel building_name_label;
    private javax.swing.JTextField building_name_enter;
    private javax.swing.JLabel street_name_label;
    private javax.swing.JTextField street_name_enter;
    private javax.swing.JLabel area_id_label;
    private javax.swing.JTextField area_id_enter;
    private javax.swing.JLabel rest_password_label;
    private javax.swing.JPasswordField rest_password_enter;
    private javax.swing.JButton rest_register;
    private javax.swing.JButton rest_login; // Login button

    
    public RestaurantRegistrationFrame() {
        initComponents();
        connectToDatabase();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        rest_id_label = new javax.swing.JLabel();
        rest_id_enter = new javax.swing.JTextField();
        rest_name_label = new javax.swing.JLabel();
        rest_name_enter = new javax.swing.JTextField();
        contact_no_label = new javax.swing.JLabel();
        contact_no_enter = new javax.swing.JTextField();
        rating_label = new javax.swing.JLabel();
        rating_enter = new javax.swing.JTextField();
        building_name_label = new javax.swing.JLabel();
        building_name_enter = new javax.swing.JTextField();
        street_name_label = new javax.swing.JLabel();
        street_name_enter = new javax.swing.JTextField();
        area_id_label = new javax.swing.JLabel();
        area_id_enter = new javax.swing.JTextField();
        rest_password_label = new javax.swing.JLabel();
        rest_password_enter = new javax.swing.JPasswordField();
        rest_register = new javax.swing.JButton();
        rest_login = new javax.swing.JButton(); // Initialize login button

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rest_id_label.setText("Restaurant ID");

        rest_name_label.setText("Restaurant Name");

        contact_no_label.setText("Contact Number");

        rating_label.setText("Rating");

        building_name_label.setText("Building Name");

        street_name_label.setText("Street Name");

        area_id_label.setText("Area ID");

        rest_password_label.setText("Password");

        rest_register.setText("REGISTER");
        rest_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rest_registerActionPerformed(evt);
            }
        });

        rest_login.setText("LOGIN"); // Setup login button
        rest_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rest_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rest_password_label)
                    .addComponent(area_id_label)
                    .addComponent(street_name_label)
                    .addComponent(building_name_label)
                    .addComponent(rating_label)
                    .addComponent(contact_no_label)
                    .addComponent(rest_name_label)
                    .addComponent(rest_id_label))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rest_id_enter, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addComponent(rest_name_enter)
                        .addComponent(contact_no_enter)
                        .addComponent(rating_enter)
                        .addComponent(building_name_enter)
                        .addComponent(street_name_enter)
                        .addComponent(area_id_enter))
                    .addComponent(rest_password_enter, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(274, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rest_login) // Add login button
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rest_register))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rest_id_label)
                        .addGap(280, 280, 280))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rest_id_label)
                    .addComponent(rest_id_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rest_name_label)
                    .addComponent(rest_name_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact_no_label)
                    .addComponent(contact_no_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rating_label)
                    .addComponent(rating_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(building_name_label)
                    .addComponent(building_name_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(street_name_label)
                    .addComponent(street_name_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(area_id_label)
                    .addComponent(area_id_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rest_password_label)
                    .addComponent(rest_password_enter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rest_register)
                    .addComponent(rest_login)) // Add login button
                .addGap(28, 28, 28))
        );

        pack();
    }

    private void rest_registerActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String query = "INSERT INTO RESTAURANT (restaurant_id, restaurant_name, contact_no, rating, building_name, street_name, area_id, password_rest) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rest_id_enter.getText());
            ps.setString(2, rest_name_enter.getText());
            ps.setString(3, contact_no_enter.getText());
            ps.setDouble(4, Double.parseDouble(rating_enter.getText()));
            ps.setString(5, building_name_enter.getText());
            ps.setString(6, street_name_enter.getText());
            ps.setInt(7, Integer.parseInt(area_id_enter.getText()));
            ps.setString(8, new String(rest_password_enter.getPassword()));
            
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                new RestaurantScreen(rest_id_enter.getText()).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please check your input.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRegistrationFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Invalid number format. Please check your input for rating and area ID.");
        }
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
    // Method for login button action
    private void rest_loginActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            new newproject11().setVisible(true); // Navigate back to login page
            this.dispose(); // Close current frame
        } catch (Exception ex) {
            Logger.getLogger(RestaurantRegistrationFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: Unable to navigate to login page.");
        }
    }

    public static void main(String args[]) {
        // Ensure this frame is called with an existing connection
    }
}
