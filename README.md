# 📦 Customer Management API

A Spring Boot RESTful web service that allows full CRUD operations on customer data, including tier-based classification (Silver, Gold, Platinum) based on spending and recent purchases.

---

## 👩‍💻 Author

**Shefali Aggarwal**  
📧 Email: [shefali@gmail.com]

---

## ✨ Features

- Create, retrieve, update, and delete customer records
- Search by ID, name, or email
- Auto-classify customers into Silver/Gold/Platinum tiers
- Global exception handling
- Logging using SLF4J with Logback
- Input validation using Jakarta Bean Validation
- In-memory H2 database for quick testing
- Comprehensive unit testing (controller, service, repository)
- No hardcoded messages — all messages are managed through constants

---

## 🛠️ Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Maven
- H2 (in-memory database)
- Lombok
- SLF4J + Logback
- JUnit 5 + Mockito

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.6+
- Git (optional)
- IDE (Eclipse, IntelliJ, or VS Code)

---

## 🚀 Getting Started

### 1. Clone the Repository

git clone https://github.com/Shefalicagg/customer-api.git
cd customer-api

## Build

mvn clean install

## During Build skip tests
mvn clean install -DskipTests

## Run with Maven
mvn spring-boot:run

## Package the application:
mvn clean package

## Run the JAR
java -jar target/customer-api-0.0.1-SNAPSHOT.jar

The application will start on: http://localhost:8080
