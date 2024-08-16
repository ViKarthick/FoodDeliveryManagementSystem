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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class orders extends javax.swing.JFrame 
{
    private Map<String, ArrayList<String>> restaurantFoodMap;
    private Map<String, String> restaurantNameIdMap;
    private Connection con;
    private int orderId;
    private static String customerId;
    private ArrayList<OrderDetails> ordersList = new ArrayList<>();
    PastOrderScreen order;
class OrderDetails {
    String restaurant;
    String food;
    String menuId;
    int quantity;
    double unitPrice;
    double cumulativeAmount;
    OrderDetails(String restaurant, String food, String menuId, int quantity) {
        this.restaurant = restaurant;
        this.food = food;
        this.menuId = menuId;
        this.quantity = quantity;
    }
    
    OrderDetails(String restaurant, String food, String menuId, int quantity, double unitPrice, double cumulativeAmount) {
        this.restaurant = restaurant;
        this.food = food;
        this.menuId = menuId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.cumulativeAmount = cumulativeAmount;
    }
}
    /**
     * Creates new form orders
     */
    public orders() {
        initComponents();
        connectToDatabase(); 
        loadRestaurantData();
        ordersList = new ArrayList<>(); // Initialize the HashMap

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        restaurant = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        food_name = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        menu_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderDetailsTextArea = new javax.swing.JTextArea();
        quantity = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        AddOrder = new javax.swing.JButton();
        FinalOrder = new javax.swing.JButton();
        reorder = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        unit_price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        restaurant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Restaurant" }));
        restaurant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restaurantActionPerformed(evt);
            }
        });

        jLabel1.setText("restaurant");

        food_name.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose food" }));
        food_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                food_nameActionPerformed(evt);
            }
        });

        jLabel2.setText("food");

        menu_id.setEditable(false);
        menu_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_idActionPerformed(evt);
            }
        });

        jLabel3.setText("menu id");

        orderDetailsTextArea.setColumns(20);
        orderDetailsTextArea.setRows(5);
        jScrollPane1.setViewportView(orderDetailsTextArea);

        quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityActionPerformed(evt);
            }
        });

        jLabel4.setText("quantity");

        AddOrder.setText("Add Order");
        AddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddOrderActionPerformed(evt);
            }
        });

        FinalOrder.setText("Final Order");
        FinalOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalOrderActionPerformed(evt);
            }
        });

        reorder.setText("Reorder");
        reorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderActionPerformed(evt);
            }
        });

        jButton1.setText("Order History");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Unit price");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(restaurant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(food_name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(unit_price))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menu_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FinalOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(menu_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(restaurant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(food_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddOrder)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(unit_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(FinalOrder)
                        .addGap(18, 18, 18)
                        .addComponent(reorder)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@LAPTOP-9M301MNP:1522:orcl",
                    "scott",
                    "boomthakkali"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void loadRestaurantData() {
    try {
        // Create statement and execute the query
        Statement statement = con.createStatement();
        String query = "SELECT r.restaurant_id, r.restaurant_name, m.food_name " +
                       "FROM restaurant r " +
                       "LEFT JOIN menu m ON r.restaurant_id = m.restaurant_id";
        ResultSet resultSet = statement.executeQuery(query);

        // Initialize maps
        restaurantFoodMap = new HashMap<>();
        restaurantNameIdMap = new HashMap<>();

        // Populate quantity dropdown
        for (int i = 1; i <= 10; i++) {
            quantity.addItem(Integer.toString(i));
        }

        // Process the result set
        while (resultSet.next()) {
            String restaurantId = resultSet.getString("restaurant_id");
            String restaurantName = resultSet.getString("restaurant_name");
            String food = resultSet.getString("food_name");

            // Add restaurant name and ID to the map
            restaurantNameIdMap.put(restaurantName, restaurantId);

            // Add food items to the corresponding restaurant
            if (!restaurantFoodMap.containsKey(restaurantName)) {
                restaurantFoodMap.put(restaurantName, new ArrayList<>());
            }

            // Only add non-null food items to the list
            if (food != null) {
                restaurantFoodMap.get(restaurantName).add(food);
            }
        }

        // Close result set and statement
        resultSet.close();
        statement.close();

        // Populate restaurant dropdown
        for (String restaurantName : restaurantFoodMap.keySet()) {
            restaurant.addItem(restaurantName);
        }
    } catch (SQLException ex) {
        // Log and show error message
        Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}


    private void menu_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menu_idActionPerformed
    
    private void populateFoodComboBox(String restaurant) {
        food_name.removeAllItems();
        ArrayList<String> foods = restaurantFoodMap.get(restaurant);
        if (foods != null) {
            for (String food : foods) {
                food_name.addItem(food);
            }
        }
    }
    private void restaurantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restaurantActionPerformed
                String selectedRestaurant = (String) restaurant.getSelectedItem();
    populateFoodComboBox(selectedRestaurant);       // TODO add your handling code here:
    }//GEN-LAST:event_restaurantActionPerformed

    private void food_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_food_nameActionPerformed
        String selectedRestaurant = (String) restaurant.getSelectedItem();
        String selectedFood = (String) food_name.getSelectedItem();
        displayMenuId(selectedRestaurant, selectedFood);        // TODO add your handling code here:
    }//GEN-LAST:event_food_nameActionPerformed

    private void AddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddOrderActionPerformed
        try {
            // TODO add your handling code here:
            addToOrderDetails();
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AddOrderActionPerformed
    
    private void addToOrderDetails() throws SQLException {
    Statement statement = con.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT MAX(order_id) + 1 AS next_order_id FROM orders");
    if (resultSet.next()) {
        orderId = resultSet.getInt("next_order_id");
    } else {
        orderId = 1; // If there are no existing orders, start from 1
    }
    resultSet.close();
    statement.close();

    String selectedRestaurantName = (String) restaurant.getSelectedItem();
    String selectedRestaurantId = restaurantNameIdMap.get(selectedRestaurantName);
    String selectedFood = (String) food_name.getSelectedItem();
    String menuId = menu_id.getText();
    String unitPriceStr = unit_price.getText();
    double unitPrice = Double.parseDouble(unitPriceStr);
    int quantity1 = Integer.parseInt((String) quantity.getSelectedItem());
    double cumulativeAmount = unitPrice * quantity1;

    OrderDetails orderDetails = new OrderDetails(selectedRestaurantName, selectedFood, menuId, quantity1, unitPrice, cumulativeAmount);

    ordersList.add(orderDetails);

    orderDetailsTextArea.append("Order ID: " + orderId + ", Menu ID: " + menuId + ", Restaurant: " + selectedRestaurantName + ", Food: " + selectedFood + ", Quantity: " + quantity1 + ", Unit Price: " + unitPrice + ", Cumulative Amount: " + cumulativeAmount + "\n");
}



    private void FinalOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalOrderActionPerformed
        // TODO add your handling code here:
        submitFinalOrder();
        OrderDisplay1.main(customerId);
        this.setVisible(false);
    }//GEN-LAST:event_FinalOrderActionPerformed

    private void reorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderActionPerformed
        // TODO add your handling code here:
        order = new PastOrderScreen(customerId);
        order.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_reorderActionPerformed

    private void quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
History obj = new History(customerId);
obj.setVisible(true);
this.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
  private void displayMenuId(String restaurantName, String food) {
    try {
        // Get the restaurant ID from the restaurant name
        String restaurantId = restaurantNameIdMap.get(restaurantName);
        // Prepare SQL statement to get the menu ID from MENU table
        String getMenuIdQuery = "SELECT menu_id FROM MENU WHERE restaurant_id = ? AND food_name = ?";
        PreparedStatement getMenuIdStatement = con.prepareStatement(getMenuIdQuery);
        getMenuIdStatement.setString(1, restaurantId);
        getMenuIdStatement.setString(2, food);

        // Execute query to get menu_id
        ResultSet menuResultSet = getMenuIdStatement.executeQuery();

        String menuId = "";
        if (menuResultSet.next()) 
        {
            menuId = menuResultSet.getString("menu_id");
            menu_id.setText(menuId);
            // Prepare SQL statement to get price from MENU_TABLE table
            String getPriceQuery = "SELECT price FROM MENU_TABLE WHERE restaurant_id = ? AND food_name = ?";
            PreparedStatement getPriceStatement = con.prepareStatement(getPriceQuery);
            getPriceStatement.setString(1, restaurantId);
            getPriceStatement.setString(2, food);

            // Execute query to get price
            ResultSet priceResultSet = getPriceStatement.executeQuery();

            if (priceResultSet.next()) {
                double price = priceResultSet.getDouble("price");
                // Assuming you have a GUI component like 'unit_price' to display the price
                unit_price.setText(((Double)price).toString()); // Set the price in your UI component
            } else {
                unit_price.setText("Price not found");
            }

            // Close price result set and prepared statement
            priceResultSet.close();
            getPriceStatement.close();

        } else {
            menu_id.setText("Menu ID not found");
            unit_price.setText("");
        }

        // Close menu result set and prepared statement
        menuResultSet.close();
        getMenuIdStatement.close();

    } catch (SQLException ex) {
        // Log and show error message
        Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}

  private boolean checkPaymentStatus(String customerId) {
    boolean paymentComplete = true;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        // Reconnect if connection is closed
        if (con == null || con.isClosed()) {
            connectToDatabase(); // Implement this method to reconnect
        }

        // Prepare the SQL statement to retrieve payment_status
        String sql = "SELECT p.payment_status " +
                     "FROM payment p " +
                     "JOIN orders o ON p.order_id = o.order_id " +
                     "WHERE o.cust_id = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, customerId);

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        // Iterate through all results
        while (resultSet.next()) {
            int paymentStatus = resultSet.getInt("payment_status");
            if (paymentStatus == 0) 
            {
                paymentComplete = false; // Found at least one incomplete payment
                break; // No need to check further
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, ex.getMessage());
    } finally {
        // Close resources in a finally block to ensure they are always closed
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
            // Consider handling or logging the exception here
        }
    }

    return paymentComplete;
}


  private int findpaymentid() throws SQLException
  {
      int payment;
     Statement statement = con.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT MAX(payment_id) + 1 AS next_payment_id FROM payment");
    if (resultSet.next()) {
        payment = resultSet.getInt("next_payment_id");
    } else {
        payment = 1; // If there are no existing orders, start from 1
    }
      return payment;
  }
// Example usage

    /**
     * @param args the command line arguments
     */
    private void submitFinalOrder() {
    PreparedStatement ordersStatement = null;
    PreparedStatement orderTableStatement = null;
    try {
        ordersStatement = con.prepareStatement(
                "INSERT INTO ORDERS (order_id, cust_id, delivery_guy_id) VALUES (?, ?, NULL)"
        );
        ordersStatement.setInt(1, orderId);
        ordersStatement.setString(2, customerId);
        ordersStatement.executeUpdate();
        ordersStatement.close();

        for (OrderDetails orderDetails : ordersList) {
            orderTableStatement = con.prepareStatement(
                    "INSERT INTO ORDER_LIST (order_id, menu_id, qty) VALUES (?, ?, ?)"
            );
            orderTableStatement.setInt(1, orderId);
            orderTableStatement.setString(2, orderDetails.menuId);
            orderTableStatement.setInt(3, orderDetails.quantity);
            orderTableStatement.executeUpdate();
            orderTableStatement.close();
        }
        
        PreparedStatement paymentStatement = con.prepareStatement(
                "INSERT INTO PAYMENT (payment_id, order_id, payment_status) VALUES (?,?,0)"
        );
        
        int payment = findpaymentid();
        System.out.println(payment);
        paymentStatement.setInt(1, payment);
        paymentStatement.setInt(2, orderId);
        paymentStatement.executeUpdate();
        paymentStatement.close();
        
        ordersList.clear(); // Clear the ArrayList after submitting the orders
        orderDetailsTextArea.setText(""); // Clear the order details text area
    } catch (SQLException ex) {
        Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, ex.getMessage());
    } finally {
        try {
            if (ordersStatement != null) {
                ordersStatement.close();
            }
            if (orderTableStatement != null) {
                orderTableStatement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    
void updateOrderDetails(ArrayList<OrderDetails> orderDetailsList)
{
    orderDetailsTextArea.setText("");
    Statement statement = null;
        try {
            statement = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT MAX(order_id) + 1 AS next_order_id FROM orders");
        } catch (SQLException ex) 
        {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (resultSet.next()) {
                orderId = resultSet.getInt("next_order_id");
            } else {
                orderId = 1; // If there are no existing orders, start from 1
            }
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    for (OrderDetails orderDetails : orderDetailsList)
    {
        ordersList.add(orderDetails);
        orderDetailsTextArea.append("Order ID: " + orderId + ", Menu ID: " + orderDetails.menuId + ", Restaurant: " + orderDetails.restaurant + ", Food: " + orderDetails.food + ", Quantity: " + orderDetails.quantity + "\n");
    }
}

private boolean checkCustomerExists(String customerId) {
    boolean exists = false;
    try {
        PreparedStatement preparedStatement;
        preparedStatement = con.prepareStatement(
                "SELECT 1 FROM orders WHERE cust_id = ?"
        );
        preparedStatement.setString(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            exists = true; // Customer exists in orders table
        }
        resultSet.close();
        preparedStatement.close();
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(orders.class.getName()).log(Level.SEVERE, null, ex);
    }
    return exists;
}
    public static void main(String customerId1) 
    {
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
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        customerId = customerId1;
        // Check if customer exists in the orders table
        orders obj = new orders();
    boolean customerExists = obj.checkCustomerExists(customerId);

    if (customerExists) {
         boolean paymentComplete = obj.checkPaymentStatus(customerId);
        
        if (paymentComplete == false) 
        {
            // Customer exists and payment status is not 0, navigate to order_display screen
            JOptionPane.showMessageDialog(null, "Order without payment found.");

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new OrderDisplay1(customerId).setVisible(true);
                }
            });
        }
        else
        {
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                new orders().setVisible(true);
                
            }
        });
        }
    } 
    else
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                new orders().setVisible(true);
                
            }
        });
    }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddOrder;
    private javax.swing.JButton FinalOrder;
    private javax.swing.JComboBox<String> food_name;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField menu_id;
    private javax.swing.JTextArea orderDetailsTextArea;
    private javax.swing.JComboBox<String> quantity;
    private javax.swing.JButton reorder;
    private javax.swing.JComboBox<String> restaurant;
    private javax.swing.JTextField unit_price;
    // End of variables declaration//GEN-END:variables
}