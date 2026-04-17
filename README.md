# 🏦 The Java Academy: Bank Management System
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white) ![Banking System](https://img.shields.io/badge/Module-Core_Banking-red?style=for-the-badge) ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white) ![Hibernate](https://img.shields.io/badge/ORM-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white) ![Swagger](https://img.shields.io/badge/-Swagger-%23C1E81C?style=for-the-badge&logo=swagger&logoColor=black)


## Description
A simple **Bank System Management** application built using **Spring Boot**.  
This project simulates basic banking operations such as managing customers, accounts, and transactions. It also demonstrates secure login and account handling using Spring Security.  
Additionally, the project includes **API documentation using Swagger** and **Email notifications** for transaction alerts.

## Features
- **User Management:** Create Account,Check account balance,Retrieve the account holder's name,Credit an amount to the user's account,Debit an amount from the user's account,Transfer funds between two accounts,Authenticate user login credentials.
- **Account Management:** Create, view, and manage bank accounts
- **Transactions:** Deposit, withdraw, and transfer money between accounts
- **Security:** Role-based authentication for admin and customers
- **Reports:** Generate account statements and transaction history# 🏦 The Java Academy: Bank Management System

A robust and secure **Banking Management System** built with **Spring Boot**, designed to handle core financial operations. This project simulates real-world banking processes, from automated email notifications to secure role-based access control.

---

## 🏗 System Architecture & API Preview

<table style="width: 100%; border-collapse: collapse;">
  <tr>
    <td style="width: 50%; text-align: center; vertical-align: top; padding: 10px;">
      <b> 📖 Swagger API Documentation</b><br><br>
      <img src="https://github.com/user-attachments/assets/41a0ab70-2546-4069-a49c-8ac0d2a79f4a" width="100%" alt="Logical Architecture">
    </td>
    <td style="width: 50%; text-align: center; vertical-align: top; padding: 10px;">
      <b>📊 SCHEMA </b><br><br>
      <img src="https://github.com/user-attachments/assets/67fc3333-a94a-4008-b1d5-eb32c654ad1a" width="100%" alt="Swagger UI">
    </td>
  </tr>
</table>

## 🌟 Key Features

### 👤 User & Account Management
* **Account Lifecycle:** Create, view, and manage bank accounts with unique identifiers.
* **Balance Inquiry:** Real-time checking of account balances and account holder details.
* **Authentication:** Secure login system to verify user credentials before accessing sensitive data.

### 💸 Transaction Engine
* **Core Operations:** Seamlessly perform **Credit** (Deposit) and **Debit** (Withdrawal) operations.
* **Fund Transfers:** Securely transfer money between two different accounts with transactional integrity.
* **Statement Generation:** View detailed transaction history and generate account statements.

### 🔒 Security & Notifications
* **Spring Security:** Implementation of **Role-Based Access Control (RBAC)** for Admin and Customer levels.
* **Email Service:** Integrated **JavaMailSender** to send automated email alerts for every transaction and account update.
* **API Documentation:** Fully documented REST endpoints using **Swagger UI** for easy testing and integration.

---

## 🛠 Tech Stack

* **Backend:** Java 17+, Spring Boot 3.x
* **Data Access:** Spring Data JPA, Hibernate
* **Database:** MySQL
* **Security:** Spring Security
* **API Docs:** Swagger / OpenAPI 3
* **Messaging:** Spring Boot Email (SMTP)
* **Build Tool:** Maven

---

## 🏗 Project Structure (Architecture)

The project follows a **Layered Architecture** to ensure separation of concerns:
1.  **Controller Layer:** Handles HTTP requests and maps them to specific endpoints.
2.  **Service Layer:** Contains the core business logic (e.g., checking for sufficient funds during transfer).
3.  **Repository Layer:** Interacts with the MySQL database using JPA.
4.  **Security Layer:** Manages authentication and authorization filters.

---

## 🚀 Getting Started

### Prerequisites
* JDK 17 or higher
* Maven 3.6+
* MySQL Server

### Installation
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/kaunghtetzaw139432/bank-system.git](https://github.com/kaunghtetzaw139432/bank-system.git)
    ```
2.  **Configure Database & Email:**
    Edit `src/main/resources/application.properties` with your MySQL and SMTP credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password

    spring.mail.host=smtp.gmail.com
    spring.mail.username=your_email@gmail.com
    spring.mail.password=your_app_password
    ```
3.  **Build and Run:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

---

## 🔗 API Documentation
Once the application is running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui.html`

---

## 👨‍💻 Developed By

**Kaung Htet Zaw**
* **Role:** Software Engineering Student & Backend Developer Intern
* **Specialization:** Java Spring Boot | C# .NET Core
* [LinkedIn Profile](https://www.linkedin.com/in/kaung-htet-zaw-backend) | [GitHub](https://github.com/kaunghtetzaw139432)
- **API Documentation:** Swagger UI for testing and exploring APIs
- **Email Service:** Sends email notifications for transactions and account updates

## Technologies Used
- **Backend:** Java, Spring Boot, Spring Data JPA, Hibernate
- **Database:** MySQL
- **Security:** Spring Security
- **API Docs:** Swagger
- **Email:** Spring Boot Email (JavaMailSender)
- **Build Tool:** Maven

## Setup Instructions
1. Clone this repository
   ```bash
   git clone https://github.com/kaunghtetzaw139432/bank-system.git
