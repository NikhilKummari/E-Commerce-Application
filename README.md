# E-Commerce-Application

This is a Java-based E-Commerce Backend Application built using Spring Boot and Maven.

It simulates how an online shopping website works where:

Users can register and login

Users can browse products

Users can add products to cart

Users can place orders

Admin can manage products

This project demonstrates backend development, REST APIs, authentication, and database integration.

🚀 Features

User Registration & Login

JWT-based Authentication

Role-Based Access Control (Admin/User)

Add / Update / Delete Products (Admin)

Browse Products with Pagination

Add to Cart

Place Orders

View Order History

Global Exception Handling

🛠️ Technologies Used

Backend

Java 17

Spring Boot

Spring Security

Spring Data JPA (Hibernate)

Database

MySQL

H2 (for testing)

Tools

Maven

Git

Postman

🏗️ Project Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database

Controller Layer handles API requests

Service Layer contains business logic

Repository Layer manages database operations

Security Layer handles authentication and authorization

💾 Database Setup

Create a database:

CREATE DATABASE ecommerce_db;

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

Run the application. Tables will be created automatically.

▶️ How to Run

Clone the repository:

git clone https://github.com/your-username/ecommerce-application.git

Navigate into the project:

cd ecommerce-application

Run the application:

mvn spring-boot:run

Open in browser:

http://localhost:8080

📌 Sample APIs

POST /api/auth/register
POST /api/auth/login
GET /api/products
POST /api/cart
POST /api/orders

📈 Future Improvements

Payment Gateway Integration

Docker Deployment

CI/CD Integration

Microservices Architecture

Frontend Integration (React / Angular)
