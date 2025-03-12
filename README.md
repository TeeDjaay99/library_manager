# Library manager with MySQL & DynamoDB

A simple library manager that handles books and loans with a **relational database(MySQL)** and a **NoSQL-solution (AWS DynamoDB)**.
The application is a **Java-terminal application** and can **synchronize data between MySQL and DynamoDB**.

### 1. Functions
* Handles books and loans
* Show books and loans from MySQL
* Synchronize books and loans to AWS DynamoDB
* User friendly menu in the terminal

### 2. System architecture 
**MySQL** is being used as the primary storage of books and loans.
**DynamoDB** is being used to mirror the MySQL-data

**Database relations**
* **One book** can be borrowed by **many borrowers over time** 📖 -> ♾️
* **One loan** connects to **one book and one borrower**

books {
(PK) book_id INT PRIMARY KEY,
book_title STRING,
author STRING,
published_year INT,
available BOOLEAN,
}

borrowers {
(PK) borrower_id INT PRIMARY KEY,
borrower_name STRING,
email STRING,
phone_number STRING,
}

loans {
(PK) loan_id INT PRIMARY KEY,
(FK) book_id INT FOREIGN KEY,
(FK) borrower_id INT FOREIGN KEY,
loan_date DATE,
return_date DATE,
}

### 3. Usage of cloud services (AWS)
* **MySQL** - Stores books, borrowers and loans.
* **DynamoDB** - Mirrors the MySQL-tables for data access.
* **AWS SDK for Java** - Used to interact with DynamoDB.

### 4. Installation and run the program
#### prerequisites
* Java version 17 or newer
* MySQL Server
* AWS DynamoDB 
### Clone the project

git clone `https://github.com/TeeDjaay99/library_manager.git`
cd library_manager

### Configure MySQL
1. Create a database with the SQL-script library_db.sql.
2. Update the MySQL connections in MySQLConnector.Java.


### 5. User Manual
### Start the application
when you start the program a menu will be shown.

Library system - menu
1. Show all books
2. Show all loans
3. Sync data to DynamoDB
4. Exit
Enter your choice:

### Functions 
1. Show all books → Retrieves all books from MySQL and displays them.
2. Show all loans → Retrieves loans from MySQL and shows who has borrowed what.
3. Syncs data to DynamoDB → Copies books and loans to DynamoDB.
4. Exit → Ends the program and closes it.

### 6. Future improvements
* **CRUD-functions** - Add the options to create, update and delete books and loans directly form the program.
* **Webinterface** - Develop a simple webapp to manage the library through a browser.
* Add some kind of other service from AWS to analyze statistics about loans.