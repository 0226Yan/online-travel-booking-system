# Online Travel Booking Management System

![Java](https://img.shields.io/badge/Java-8-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.6-brightgreen)
![Vue](https://img.shields.io/badge/Vue-2.x-42b883)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![MyBatis](https://img.shields.io/badge/MyBatis-Persistence-red)
![JWT](https://img.shields.io/badge/JWT-Authentication-purple)

![Project Preview](./screenshots/preview.png)

## Overview

This project is a full-stack online travel booking management system built with **Vue 2**, **Spring Boot**, and **MySQL**.

The system includes two main parts:

- **User Portal**: allows users to browse and book hotels, scenic spots, travel routes, and flights.
- **Admin Dashboard**: allows administrators to manage users, hotels, rooms, scenic spots, routes, flights, notices, and orders.

This project was originally developed as an undergraduate capstone project.

---

## Tech Stack

| Layer | Technologies |
| --- | --- |
| Frontend | Vue 2, Vue Router, Element UI, Axios |
| Backend | Spring Boot, Spring MVC, Java |
| Database & Persistence | MySQL, MyBatis |
| Authentication | JWT |
| Build Tools | Maven, Vue CLI |

---

## Features

### User Portal

- User registration and login
- User profile management
- Browse hotels, scenic spots, travel routes, and flights
- Book hotels, tickets, routes, and flights
- View, pay, and cancel personal orders
- View system notices

### Admin Dashboard

- Admin login
- User and admin management
- Hotel, room, and room number management
- Scenic spot management
- Travel route management
- Flight management
- Hotel order, ticket order, route order, and flight order management
- Notice management
- Image upload and display

---

## Screenshots

### User Portal

#### Login

![Login Page](./screenshots/login.png)

#### Home Page

![User Home](./screenshots/user-home.png)

#### Hotel Search and Listing

![Hotel List](./screenshots/hotel-list.png)

#### Hotel Details

![Hotel Details](./screenshots/hotel-details.png)

#### Booking Confirmation

![Booking Confirmation](./screenshots/booking-dialog.png)

#### User Orders

![User Orders](./screenshots/user-orders.png)

### Admin Dashboard

#### Scenic Spot Management

![Scenic Spot Management](./screenshots/admin-spot-management.png)

#### Order Management

![Order Management](./screenshots/admin-order-management.png)
---

## Project Structure

```text
project_travel/
├── README.md                         # Project documentation
├── .gitignore                        # Git ignored files
│
├── database/
│   └── travel_management_db.sql      # Database schema and sample data
│
├── screenshots/                      # Screenshots used in README
│   ├── preview.png
│   ├── login.png
│   ├── user-home.png
│   ├── hotel-list.png
│   ├── hotel-details.png
│   ├── booking-dialog.png
│   ├── user-orders.png
│   ├── admin-spot-management.png
│   └── admin-order-management.png
│
├── springboot/                       # Backend project
│   ├── src/main/java/com/example/
│   │   ├── common/                   # JWT, interceptors, and response wrapper
│   │   ├── controller/               # Controller layer
│   │   ├── dao/                      # Data access layer
│   │   ├── entity/                   # Entity classes
│   │   ├── exception/                # Exception handling
│   │   └── service/                  # Business logic layer
│   ├── src/main/resources/
│   │   ├── mapper/                   # MyBatis XML mapper files
│   │   └── application.yml           # Backend configuration
│   ├── file/                         # Uploaded image resources
│   └── pom.xml                       # Maven configuration
│
└── vue/                              # Frontend project
    ├── public/                       # Static assets
    ├── src/
    │   ├── router/                   # Vue Router configuration
    │   ├── utils/                    # Axios request configuration
    │   ├── views/admin/              # Admin dashboard pages
    │   └── views/front/              # User portal pages
    ├── package.json
    └── vue.config.js
```

---

## Demo Accounts

### Admin Account

```text
Username: admin
Password: 123
Role: Administrator
```

### User Account

```text
Username: demo_user
Password: 123
Role: User
```

---

## Local Setup

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE travel_management_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Import the database file:

```bash
mysql -u root -p travel_management_db < database/travel_management_db.sql
```

Update the backend database configuration file:

```text
springboot/src/main/resources/application.yml
```

Example configuration:

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: your_mysql_password
    url: jdbc:mysql://localhost:3306/travel_management_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2b8&allowPublicKeyRetrieval=true
```

---

### 2. Start the Backend

Open the project in IntelliJ IDEA and run:

```text
springboot/src/main/java/com/example/SpringbootApplication.java
```

The backend runs at:

```text
http://localhost:8080
```

API prefix:

```text
http://localhost:8080/api
```

---

### 3. Start the Frontend

Go to the frontend directory:

```bash
cd vue
npm install
npm run serve
```

The frontend runs at:

```text
http://localhost:8081
```

The frontend API base URL is configured in:

```text
vue/src/utils/request.js
```

---

## Image Resources

Login page images and other frontend static assets are stored in:

```text
vue/public
```

Uploaded images such as user avatars, hotels, rooms, scenic spots, and travel routes are stored in:

```text
springboot/file
```

---

## Development Notes

- Developed and refined major pages for both the user portal and admin dashboard.
- Integrated frontend components with backend REST APIs.
- Configured the MySQL database and imported sample data.
- Tested the complete local workflow, including frontend, backend, database, and image resources.
