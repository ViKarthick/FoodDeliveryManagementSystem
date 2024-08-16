package dbmsproject.dbmsproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeliveryGuyDashboardUI extends JFrame {

    private JTextArea detailsTextArea = new JTextArea(10, 30);
    private Connection con;
    private String deliveryGuyId;

    public DeliveryGuyDashboardUI(String deliveryGuyId) 
    {
        super("Delivery Guy Dashboard");
        this.deliveryGuyId = deliveryGuyId;
        con = getConnection();
        initComponents();
        setupUI();
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Automatically fetch and display initial customer details
        viewCustomerDetails();
    }

    private void initComponents() 
    {
        detailsTextArea.setEditable(false);
    }

    private void setupUI() {
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Delivery Guy ID: ");
        JTextField idField = new JTextField(deliveryGuyId, 10);
        idField.setEditable(false);
        topPanel.add(idLabel);
        topPanel.add(idField);
        JButton viewDetailsButton = new JButton("View Customer Details");
        topPanel.add(viewDetailsButton);
        
        // Add back button
        JButton backButton = new JButton("Back");
        topPanel.add(backButton);

        contentPane.add(topPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(detailsTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCustomerDetails();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // Navigate back to the login screen or any other desired screen
                // Example: Replace with the appropriate class and method to show login screen
                DeliveryOption.main(deliveryGuyId); // Assumes a static main method in LoginUI class
            }
        });

        setContentPane(contentPane);
    }

    private void viewCustomerDetails() 
    {
        try 
        {
            // Query orders_table to get cust_id
            PreparedStatement ordersStatement = con.prepareStatement(
                    "SELECT cust_id FROM orders WHERE delivery_guy_id = ? and delivered = 1");
            ordersStatement.setString(1, deliveryGuyId);
            ResultSet ordersResult = ordersStatement.executeQuery();

            while (ordersResult.next()) 
            {
                String custId = ordersResult.getString("cust_id");
                // Query customer table to get customer details
                PreparedStatement customerStatement = con.prepareStatement(
                        "SELECT cust_name, ph_no, flat_no, street_name, area_id FROM customer WHERE cust_id = ?");
                customerStatement.setString(1, custId);
                ResultSet customerResult = customerStatement.executeQuery();
                StringBuilder details = new StringBuilder();
                
        int c = 0;
        while (customerResult.next()) 
        {
            String custName = customerResult.getString("cust_name");
            String custPhone = customerResult.getString("ph_no");
            String flatNo = customerResult.getString("flat_no");
            String streetName = customerResult.getString("street_name");
            int areaId = customerResult.getInt("area_id");

            // Query area table to get area name
            String areaName = getAreaName(areaId);

            // Append customer details to the StringBuilder
            details.append("Customer Details:\n")
                   .append("Name: ").append(custName).append("\n")
                   .append("Phone: ").append(custPhone).append("\n")
                   .append("Address: ").append(flatNo).append(", ").append(streetName).append(", ").append(areaName)
                   .append("\n\n"); // Separate records with double newline
            // Check if any records were found
            if (details.length() > 0) 
            {
                detailsTextArea.append(details.toString());
                c=1;
            } else if(c == 0) {
                detailsTextArea.append("No customers found.");
                break;
            }

        }

                customerResult.close();
                customerStatement.close();
            }

            ordersResult.close();
            ordersStatement.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to get area name based on area_id
    private String getAreaName(int areaId) {
        String areaName = "";
        try {
            PreparedStatement areaStatement = con.prepareStatement(
                    "SELECT area_name FROM area WHERE area_id = ?");
            areaStatement.setInt(1, areaId);
            ResultSet areaResult = areaStatement.executeQuery();

            if (areaResult.next()) {
                areaName = areaResult.getString("area_name");
            }

            areaResult.close();
            areaStatement.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return areaName;
    }

    // Method to establish database connection
    private Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@LAPTOP-9M301MNP:1522:orcl",
                    "scott",
                    "boomthakkali"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to database: " + ex.getMessage());
        }
        return con;
    }

    public static void main(String deliveryGuyId) {
        // Example usage:
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DeliveryGuyDashboardUI(deliveryGuyId);
            }
        });
    }
}