/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmsproject.dbmsproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditCustomerScreen extends JFrame {

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField flatNoField;
    private JTextField streetNameField;
    private JComboBox<String> areaComboBox;
    private JButton saveButton;
    private Connection con;
    private String customerId;
    
    public EditCustomerScreen(String customerId) 
    {
        this.customerId = customerId;
        initComponents();
        connectToDatabase(); // Connect to your database here
        loadCustomerDetails(); // Load customer details into fields
        loadAreaData(); // Load area data into combo box
    }

    private void initComponents() {
        setTitle("Edit Customer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Flat No:"));
        flatNoField = new JTextField();
        panel.add(flatNoField);

        panel.add(new JLabel("Street Name:"));
        streetNameField = new JTextField();
        panel.add(streetNameField);

        panel.add(new JLabel("Area:"));
        areaComboBox = new JComboBox<>();
        panel.add(areaComboBox);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveCustomerDetails();
            }
        });
        panel.add(saveButton);
        add(panel, BorderLayout.CENTER);
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

    private void loadCustomerDetails() {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT cust_name, ph_no, flat_no, street_name, area_id FROM customer WHERE cust_id = ?"
            );
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nameField.setText(resultSet.getString("cust_name"));
                phoneField.setText(resultSet.getString("ph_no"));
                flatNoField.setText(resultSet.getString("flat_no"));
                streetNameField.setText(resultSet.getString("street_name"));
                String areaId = resultSet.getString("area_id");
                selectAreaInComboBox(areaId);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching customer details: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadAreaData() {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT area_id, area_name FROM area"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String areaId = resultSet.getString("area_id");
                String areaName = resultSet.getString("area_name");
                areaComboBox.addItem(areaId + " - " + areaName);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching area data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void selectAreaInComboBox(String areaId) {
        for (int i = 0; i < areaComboBox.getItemCount(); i++) {
            String item = areaComboBox.getItemAt(i);
            if (item.startsWith(areaId)) {
                areaComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void saveCustomerDetails() {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE customer SET cust_name = ?, ph_no = ?, flat_no = ?, street_name = ?, area_id = ? WHERE cust_id = ?"
            );
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, phoneField.getText());
            preparedStatement.setString(3, flatNoField.getText());
            preparedStatement.setString(4, streetNameField.getText());
            // Extract area_id from selected combo box item
            String selectedAreaItem = (String) areaComboBox.getSelectedItem();
            String areaId = selectedAreaItem.split(" - ")[0];
            preparedStatement.setString(5, areaId);
            preparedStatement.setString(6, customerId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Customer details updated successfully.");
                CustomerEditOrder obj = new CustomerEditOrder(customerId);
                obj.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update customer details.");
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating customer details: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String customerId) {
        

        // Retrieve customerId from command line arguments

        // Initialize GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EditCustomerScreen(customerId).setVisible(true);
            }
        });
    }
}