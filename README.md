# Food Delivery Management System

A Java Swing desktop application backed by an **Oracle SQL** database, built as an academic DBMS project. It models the end-to-end workflow of a simplified food delivery platform — customers browsing restaurants and placing orders, restaurants managing their menus, delivery personnel handling assigned orders, and payments — with an emphasis on normalized relational schema design and JDBC-based application integration.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-blue)
![Oracle](https://img.shields.io/badge/Database-Oracle%20SQL-F80000?logo=oracle&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)

---

## Table of Contents

- [Overview](#overview)
- [Key Components](#key-components)
- [Tech Stack](#tech-stack)
- [Data Model](#data-model)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Database Configuration](#database-configuration)
- [Known Limitations](#known-limitations)
- [Design Focus & Learning Outcomes](#design-focus--learning-outcomes)

---

## Overview

The system implements three user roles, each with their own login and dashboard:

- **Customer** — registers, logs in, browses restaurants/menus, places orders, views order history, reorders past items, and pays.
- **Delivery personnel** — registers, logs in, views currently assigned deliveries, and updates delivery status.
- **Restaurant** — registers and manages its own menu items.

The project's primary goals were to:
1. Design a consistent, normalized relational schema for real-world entities (customers, restaurants, menu items, delivery personnel, orders, payments).
2. Work through functional dependencies and normalize the schema (up to 3NF/BCNF).
3. Implement the schema in Oracle SQL with primary/foreign keys, unique constraints, and check constraints.
4. Integrate the schema with a Java Swing client via JDBC for inserting, updating, and querying records (customer registration, order placement, menu management, payment processing).

## Key Components

### Data Modelling & Normalization
- ER model covering customers, restaurants, menus, orders, order line items, delivery personnel, payments, and areas (used for address/locality lookups).
- Functional dependencies identified and the schema decomposed to 3NF/BCNF to remove redundancy.

### SQL Schema & Constraints
- Oracle-based tables including `CUSTOMER`, `RESTAURANT`, `MENU`, `AREA`, `ORDERS`, `DELIVERY_GUY`, and `PAYMENT`, plus an order-line table linking orders to menu items and quantities.
- Constraint design including primary/foreign keys, unique IDs (e.g. rejecting duplicate `cust_id`, `menu_id`), and default values (e.g. new delivery personnel start with `order_count = 0` and a `star_rating` of 5).

### Application Layer (Java Swing + JDBC)
Each screen is a self-contained Swing frame backed directly by JDBC calls:

| Screen | Purpose |
|---|---|
| `customer1` / `register1` | Customer login and registration |
| `RestaurantRegistrationFrame` / `RestaurantScreen` | Restaurant registration and dashboard |
| `MenuFrame` | Add/manage menu items for a restaurant |
| `orders` / `OrderDisplay1` | Place an order and view order details, including assigned delivery personnel |
| `DeliveryLogin` / `RegisterDeliveryGuy` | Delivery personnel login and registration |
| `DeliveryOption` / `DeliveryChoose` / `DeliveryCurrent` / `DeliveryGuyDashboardUI` | Delivery personnel dashboard and current-assignment handling |
| `Payment` | Marks an order as paid |
| `History` / `PastOrderScreen` | Customer order history and reordering |
| `EditCustomerScreen` / `EditDeliveryGuy` / `CustomerEditOrder` | Editing existing customer, delivery-personnel, or order records |
| `DbmsMain` / `Dbmsproject` | Application entry point and main menu |

JDBC is used with `PreparedStatement`s throughout for parameterized inserts, updates, and lookups (e.g. login checks, duplicate-ID checks, order history queries).

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Java Swing (NetBeans GUI Builder `.form` files) |
| Data access | JDBC (`oracle.jdbc.OracleDriver`) |
| Database | Oracle SQL |
| Build | Maven (`ojdbc7` driver dependency) |
| Language level | Java 17 |

## Data Model

Core entities inferred from the application queries:

- **AREA** — locality lookup (`area_id`, `area_name`) used by both customers and restaurants for address fields.
- **CUSTOMER** — `cust_id`, `password`, `cust_name`, `ph_no`, `flat_no`, `street_name`, `area_id`.
- **RESTAURANT** — `restaurant_id`, `restaurant_name`, `contact_no`, `rating`, `building_name`, `street_name`, `area_id`, `password_rest`.
- **MENU** — menu items per restaurant (`menu_id`, `restaurant_id`, `food_name`, ...).
- **ORDERS** — `order_id`, links to `cust_id`, `delivery_guy_id`, and a `delivered` flag.
- **Order line items** — join table linking an order to one or more `menu_id`s and quantities.
- **DELIVERY_GUY** — `delivery_guy_id`, `delivery_guy_name`, `ph_no`, `vehicle_no`, `order_count`, `star_rating`.
- **PAYMENT** — payment status per order.

## Project Structure

```
FoodDeliveryManagementSystem/
├── pom.xml
└── src/main/java/dbmsproject/dbmsproject/
    ├── Dbmsproject.java          # main() entry point
    ├── DbmsMain.java / .form     # main menu
    ├── customer1.java / register1.java              # customer login/registration
    ├── RestaurantRegistrationFrame.java / RestaurantScreen.java
    ├── MenuFrame.java
    ├── orders.java / OrderDisplay1.java
    ├── DeliveryLogin.java / RegisterDeliveryGuy.java
    ├── DeliveryOption.java / DeliveryChoose.java / DeliveryCurrent.java / DeliveryGuyDashboardUI.java
    ├── Payment.java
    ├── History.java / PastOrderScreen.java
    ├── EditCustomerScreen.java / EditDeliveryGuy.java / CustomerEditOrder.java
    └── newproject11.java / newproject22.java
```

> `.form` files are NetBeans GUI Builder layout definitions paired with their `.java` counterparts — the project is set up to be opened/edited in **NetBeans**, even though it also builds with plain Maven.

## Getting Started

### Prerequisites
- JDK 17
- Maven
- An accessible Oracle Database instance (local or remote), with the schema described above created ahead of time
- (Recommended) NetBeans IDE, since the UI screens are NetBeans `.form`-based

### Build & Run

```bash
git clone https://github.com/ViKarthick/FoodDeliveryManagementSystem.git
cd FoodDeliveryManagementSystem
mvn compile
mvn exec:java -Dexec.mainClass=dbmsproject.dbmsproject.Dbmsproject
```

Or open the project in NetBeans and run it directly — the GUI builder needs NetBeans to render `.form` files properly if you plan to edit the UI.

## Database Configuration

Each screen currently opens its own JDBC connection independently, with the Oracle host, port, SID, and credentials **hardcoded inline**, e.g.:

```java
Class.forName("oracle.jdbc.OracleDriver");
con = DriverManager.getConnection("jdbc:oracle:thin:@<host>:<port>:<sid>", "<user>", "<password>");
```

To run this against your own database:
1. Create the schema (`AREA`, `CUSTOMER`, `RESTAURANT`, `MENU`, `ORDERS`, order-line table, `DELIVERY_GUY`, `PAYMENT`) in your Oracle instance.

## Design Focus & Learning Outcomes

This project demonstrates:
- Designing structured, normalized, and scalable relational data models.
- Applying database theory — functional dependencies, decomposition, referential integrity.
- Implementing relational schemas in Oracle SQL with real constraint enforcement.
- Integrating a desktop front end with a relational backend via JDBC.
- Translating conceptual ER models into a working multi-role application.
