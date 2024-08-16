/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dbmsproject.dbmsproject;

import java.text.DecimalFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class OrderDisplay1 extends javax.swing.JFrame 
{
    Connection con;
    private String customerId;
    private orders order;
    int orderId;
    String payment;
    public OrderDisplay1(String customerId) 
    {
        initComponents();
        this.customerId = customerId;
        displayOrderDetails();
    }
    private Connection getConnection() throws SQLException 
    {
        Connection con = null;
        try 
        {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@LAPTOP-9M301MNP:1522:orcl",
                    "scott",
                    "boomthakkali"
            );
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(OrderDisplay1.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database driver not found: " + ex.getMessage());
        }
        return con;
    }
   private void displayOrderDetails() {
    try {
        Connection con = getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(
            "SELECT o.order_id, mt.restaurant_id, mt.food_name, m.restaurant_id AS rest_name, " +
            "ol.menu_id, ol.qty, m.price AS unit_price, o.delivery_guy_id " +
            "FROM orders o " +
            "JOIN ORDER_LIST ol ON o.order_id = ol.order_id " +
            "JOIN MENU mt ON ol.menu_id = mt.menu_id " +
            "JOIN MENU_TABLE m ON mt.restaurant_id = m.restaurant_id AND mt.food_name = m.food_name " +
            "JOIN payment p ON o.order_id = p.order_id " + // Join with payment table
            "WHERE o.cust_id = ? AND p.payment_status = 0 " + // Filter by payment_status = 0
            "ORDER BY o.order_id"
        );

        preparedStatement.setString(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        StringBuilder sb = new StringBuilder();
        boolean deliveryGuyAssigned = false;
        double totalPayment = 0.0;

        while (resultSet.next()) {
            orderId = resultSet.getInt("order_id");
            String restaurantId = resultSet.getString("restaurant_id");
            String foodName = resultSet.getString("food_name");
            String restaurantName = resultSet.getString("rest_name");
            String menuId = resultSet.getString("menu_id");
            int quantity = resultSet.getInt("qty");
            double unitPrice = resultSet.getDouble("unit_price");
            String deliveryGuyId = resultSet.getString("delivery_guy_id");

            double paymentAmount = quantity * unitPrice;

            sb.append("Order ID: ").append(orderId)
              .append(", Restaurant ID: ").append(restaurantId)
              .append(", Restaurant Name: ").append(restaurantName)
              .append(", Food Name: ").append(foodName)
              .append(", Menu ID: ").append(menuId)
              .append(", Quantity: ").append(quantity)
              .append(", Payment Amount: ").append(paymentAmount)
              .append("\n");

            totalPayment += paymentAmount;

            if (deliveryGuyId != null) {
                displayDeliveryGuyDetails(con, deliveryGuyId);
                deliveryGuyAssigned = true;
            }
        }
        
        resultSet.close();
        preparedStatement.close();
        con.close();
        
        // Display order details in text area or any other component
        orderDetailsTextArea.setText(sb.toString());
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2);
        String formattedTotalPayment = df.format(totalPayment);
        payment = formattedTotalPayment;
        jTextField2.setText(formattedTotalPayment);
        if (!deliveryGuyAssigned) {
            assignDeliveryGuy(con); // Assuming you need to pass the connection object here
        }

    } catch (SQLException ex) {
        Logger.getLogger(OrderDisplay1.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error fetching order details: " + ex.getMessage());
    }
}



    private void displayDeliveryGuyDetails(Connection con, String deliveryGuyId) 
    {
        try {
            if (con == null || con.isClosed()) {
            con = getConnection(); // Implement this method to reconnect
        }
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT delivery_guy_name, ph_no " +
                            "FROM delivery_guy " +
                            "WHERE delivery_guy_id = ?"
            );
            preparedStatement.setString(1, deliveryGuyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String deliveryGuyName = resultSet.getString("delivery_guy_name");
                String deliveryGuyPhNo = resultSet.getString("ph_no");

                jTextField3.setText(deliveryGuyName);
                jTextField4.setText(deliveryGuyPhNo);
            } else {
                jTextField3.setText(null);
                jTextField4.setText(null);
                JOptionPane.showMessageDialog(this, "Delivery guy details not found");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDisplay1.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error fetching delivery guy details: " + ex.getMessage());
        }
    }

    private void assignDeliveryGuy(Connection con) {
        String deliveryGuyId = "";
        try {
             if (con == null || con.isClosed()) {
           con = getConnection(); // Implement this method to reconnect
        }

            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT delivery_guy_id, delivery_guy_name, ph_no " +
                            "FROM delivery_guy " +
                            "WHERE order_count = 0 " +
                            "AND ROWNUM = 1"
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                deliveryGuyId = resultSet.getString("delivery_guy_id");
                String deliveryGuyName = resultSet.getString("delivery_guy_name");
                String deliveryGuyPhNo = resultSet.getString("ph_no");

                // Update delivery_guy_id in orders table
                PreparedStatement updateStatement = con.prepareStatement(
                        "UPDATE orders " +
                                "SET delivery_guy_id = ? " +
                                "WHERE cust_id = ?"
                );
                updateStatement.setString(1, deliveryGuyId);
                updateStatement.setString(2, customerId);
                updateStatement.executeUpdate();
                updateStatement.close();

                // Update order_count in delivery_guy table
                PreparedStatement updateOrderCountStatement = con.prepareStatement(
                        "UPDATE delivery_guy " +
                                "SET order_count = 1 " +
                                "WHERE delivery_guy_id = ?"
                );
                updateOrderCountStatement.setString(1, deliveryGuyId);
                updateOrderCountStatement.executeUpdate();
                updateOrderCountStatement.close();

                jTextField3.setText(deliveryGuyName);
                jTextField4.setText(deliveryGuyPhNo);
            } else {
                jTextField3.setText(null);
                jTextField4.setText(null);
                JOptionPane.showMessageDialog(this, "No delivery guys available");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDisplay1.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error assigning delivery guy: " + ex.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paymentButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderDetailsTextArea = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Order = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        paymentButton.setText("Pay Bill");
        paymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentButtonActionPerformed(evt);
            }
        });

        orderDetailsTextArea.setEditable(false);
        orderDetailsTextArea.setColumns(20);
        orderDetailsTextArea.setRows(5);
        jScrollPane2.setViewportView(orderDetailsTextArea);

        jTextField1.setEditable(false);
        jTextField1.setText("Your Current Order:");

        jLabel1.setText("Payment Amount:");

        Order.setText("New Order");
        Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderActionPerformed(evt);
            }
        });

        jLabel2.setText("Delivery Guy Name");

        jLabel3.setText("Delivery Guy Phone");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(Order, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(paymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4)))
                        .addGap(163, 163, 163))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Order)
                        .addGap(35, 35, 35)
                        .addComponent(paymentButton)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderActionPerformed
        order = new orders();
        this.setVisible(false);
        order.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_OrderActionPerformed

    private void paymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentButtonActionPerformed
System.out.println(orderId + payment);
new Payment(orderId,payment,customerId).setVisible(true);
this.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_paymentButtonActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    /**
     * @param customerId
     */
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderDisplay1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderDisplay1(customerId).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Order;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextArea orderDetailsTextArea;
    private javax.swing.JButton paymentButton;
    // End of variables declaration//GEN-END:variables

}
