# Food Delivery Management System

This project implements a database-driven Food Delivery Management System designed to demonstrate strong foundations in relational modelling, normalization, integrity constraint design, and database-application integration. It was developed as part of an academic exercise but structured with production-style data modelling practices and clear justifications for all schema decisions, making it suitable to showcase in a Master's application portfolio.

---

## Project Overview

The system models the workflow of a simplified food delivery platform through a complete relational schema and a Java-based front-end. The primary goals of this project were:

1. To design a consistent and normalized database schema for real-world entities such as customers, restaurants, menu items, delivery personnel, orders, and payments.
2. To identify functional dependencies across all entities and perform step-by-step normalization (up to 3NF/BCNF).
3. To implement the schema in Oracle SQL with appropriate constraints, including primary/foreign keys, unique constraints, check constraints, and references.
4. To integrate the database with a Java Swing client using JDBC for performing operations such as inserting records, placing orders, updating menu items, and validating payment details.

This project highlights the ability to translate conceptual data models into a working application while maintaining data consistency and correctness.

---

## Key Components

### 1. Data Modelling and Normalization
- Complete ER model representing all major entities and their relationships.
- Identification of functional dependencies across each entity.
- Systematic decomposition of the schema into 3NF/BCNF to remove redundancy and anomalies.
- Final relational schema constructed with clear justification and mapping from the ER diagram.

### 2. SQL Schema and Constraints
- Oracle-based schema creation scripts covering all entities: AREA, CUSTOMER, RESTAURANT, MENU, ORDERS, DELIVERY_GUY, PAYMENT, and linking relations.
- Comprehensive constraint design including:
  - Primary and foreign keys  
  - Unique constraints  
  - Not-null constraints  
  - Domain restrictions using check constraints  
- Example: availability status, food category, quantity bounds, and rating validations.

This ensures the database maintains strong integrity properties and supports consistent transactions.

### 3. Application Layer (Java + JDBC)
- A Java Swing client application serves as the interface to the database.
- Implements CRUD operations and basic operational logic such as:
  - Adding customers and restaurants
  - Updating menu items
  - Assigning delivery personnel
  - Placing orders linked to menu items and customers
  - Processing payments with validation
- JDBC is used through prepared statements and stored procedures to ensure secure and efficient database interaction.

---

## Design Focus and Learning Outcomes

This project demonstrates:
- Ability to design structured, normalized, and scalable data models.
- Understanding of database theory (functional dependencies, decomposition, referential integrity).
- Practical experience in implementing relational schemas in Oracle SQL.
- Integration of a front-end application with a relational backend using JDBC.
- Clean translation of conceptual models into working systems.

Such skills form a foundational part of graduate-level coursework and research in databases, information systems, data engineering, and software systems.
