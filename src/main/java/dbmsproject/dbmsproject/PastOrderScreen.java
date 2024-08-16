/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dbmsproject.dbmsproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PastOrderScreen extends javax.swing.JFrame 
{

    private String customerId;
    private int orderId;
    Connection con;
    private ArrayList<orders.OrderDetails> reorderedItems;
    
    public PastOrderScreen(String customerId) 
    {
        this.customerId = customerId;
        initComponents();
        reorderedItems = new ArrayList<>();
        displayOrderDetails();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ordersComboBox = new javax.swing.JComboBox<>();
        reorderButton = new javax.swing.JButton();
        seeOrderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ordersComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Loading orders..." }));

        reorderButton.setText("Reorder Selected");
        reorderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderButtonActionPerformed(evt);
            }
        });

        seeOrderButton.setText("See Order Details");
        seeOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seeOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ordersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(seeOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(reorderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reorderButton)
                    .addComponent(seeOrderButton))
                .addGap(51, 51, 51)
                .addComponent(ordersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reorderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderButtonActionPerformed
String selectedOrder = (String) ordersComboBox.getSelectedItem();
        if (selectedOrder != null) {
            String[] orderDetails = selectedOrder.split(" - ");
            if (orderDetails.length == 5) {
                try {
                    String restaurant = orderDetails[1].split(": ")[1].trim();
                    String food = orderDetails[2].split(": ")[1].trim();
                    String menuId = orderDetails[3].split(": ")[1].trim();
                    int quantity = Integer.parseInt(orderDetails[4].split(": ")[1].trim());

                    // Create an instance of orders if not already available
                    orders obj = new orders();

                    // Create an OrderDetails object using the instance of orders
                    orders.OrderDetails reorderedItem = obj.new OrderDetails(restaurant, food, menuId, quantity);

                    // Add the reordered item to the reorderedItems ArrayList
                    reorderedItems.add(reorderedItem);

                    // Optionally, perform reorder action if needed
                    
                    //reorder(menuId, quantity);

                    // Optionally, notify user or update UI
                    JOptionPane.showMessageDialog(PastOrderScreen.this, "Item added to reorder list: " + restaurant + " - " + food);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PastOrderScreen.this, "Invalid quantity format.");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(PastOrderScreen.this, "Invalid order details format.");
                }
            } else {
                JOptionPane.showMessageDialog(PastOrderScreen.this, "Selected order details are not in the expected format.");
            }
        } else {
            JOptionPane.showMessageDialog(PastOrderScreen.this, "Please select an order to reorder.");
        }
            // TODO add your handling code here:
    }//GEN-LAST:event_reorderButtonActionPerformed

    private void seeOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seeOrderButtonActionPerformed
                orders orderScreen = new orders();
                orderScreen.updateOrderDetails(reorderedItems); // Pass reordered items to orders screen
                orderScreen.setVisible(true);
                PastOrderScreen.this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_seeOrderButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    
   private void displayOrderDetails() {
    try {
        con = getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(
                "SELECT o.order_id, m.restaurant_id AS rest_name, m.food_name, ot.menu_id, ot.qty " +
                        "FROM orders o " +
                        "JOIN order_list ot ON o.order_id = ot.order_id " +
                        "JOIN menu m ON ot.menu_id = m.menu_id " +
                        "WHERE o.cust_id = ? " +
                        "ORDER BY o.order_id"
        )) {
            preparedStatement.setString(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderId = resultSet.getInt("order_id");
                    String restaurantName = resultSet.getString("rest_name");
                    String foodName = resultSet.getString("food_name");
                    String menuId = resultSet.getString("menu_id");
                    int quantity = resultSet.getInt("qty");  // Use qty instead of quantity

                    String orderDetail = "Order ID: " + orderId + " - Restaurant: " + restaurantName +
                            " - Food: " + foodName + " - Menu ID: " + menuId + " - Quantity: " + quantity;
                    ordersComboBox.addItem(orderDetail);
                }
            }
        }
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(PastOrderScreen.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error fetching order details: " + ex.getMessage());
    }
}

    private Connection getConnection() throws SQLException 
    {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@LAPTOP-9M301MNP:1522:orcl",
                    "scott",
                    "boomthakkali"
            );
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PastOrderScreen.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database driver not found: " + ex.getMessage());
        }
        return con;
    }
    
    public static void main(String customerId) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PastOrderScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PastOrderScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PastOrderScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PastOrderScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PastOrderScreen(customerId).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ordersComboBox;
    private javax.swing.JButton reorderButton;
    private javax.swing.JButton seeOrderButton;
    // End of variables declaration//GEN-END:variables
}
