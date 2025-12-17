# Webforest Employee Management System

A Spring Boot REST API application for managing employee records with CRUD operations.

## Technology Stack

- **Java 17**
- **Spring Boot 4.0.0**
- **Spring Data JPA**
- **Hibernate 7.1.8**
- **MySQL 8.0**
- **Maven**

## Features

- Create, Read, Update, and Delete employee records
- RESTful API endpoints
- JPA entity management with optimistic locking
- MySQL database integration
- H2 console for database management
- Spring Boot DevTools for development
- Actuator for monitoring

## Prerequisites

- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+

## Database Configuration

The application connects to MySQL database with the following configuration:

```properties
Database: webforest_db
Host: localhost:3306
Username: 
Password: 
```

Make sure to create the database before running the application:

```sql
CREATE DATABASE webforest_db;
```

## Running the Application

### Using Maven

```bash
mvn clean compile
mvn spring-boot:run
```

### Using Java

```bash
mvn clean package
java -jar target/webforest-0.0.1-SNAPSHOT.jar
```

The application will start on **port 8082**.

## API Endpoints

Base URL: `http://localhost:8082/api/v1`

### Get All Employees
```
GET /employees
```

### Get Employee by ID
```
GET /employees/{id}
```

### Create Employee
```
POST /employees
Content-Type: application/json

{
  "name": "John Doe",
  "age": 30,
  "department": "IT"
}
```

### Update Employee
```
PUT /employees/{id}
Content-Type: application/json

{
  "name": "John Doe",
  "age": 31,
  "department": "Engineering"
}
```

### Delete Employee
```
DELETE /employees?id={id}
```

## Entity Structure

### EmployeeEntity

- `id` (Long) - Auto-generated primary key
- `name` (String) - Employee name
- `age` (Integer) - Employee age
- `department` (String) - Employee department
- `version` (Long) - Optimistic locking version field

## Key Features

### Optimistic Locking

The application uses JPA's `@Version` annotation to handle concurrent updates and prevent lost updates in multi-user scenarios.

### Error Handling

- Input validation for required fields
- Optimistic locking failure handling
- Custom error messages for better debugging

## Known Issues

### Lombok Compatibility

⚠️ **Important:** This project does **not use Lombok** due to compilation compatibility issues with the current build environment (Java 25 + Maven compiler 3.14.1).

**Issue Details:**
- Lombok annotation processing causes `java.lang.ExceptionInInitializerError: com.sun.tools.javac.code.TypeTag` during Maven compilation
- The error occurs even with Lombok versions 1.18.30, 1.18.34, and 1.18.36
- Spring Boot 4.0.0 managed Lombok version also encounters the same issue

**Solution:**
All entity classes use **manual getters, setters, and constructors** instead of Lombok annotations (`@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`).

If you want to use Lombok in your environment:
1. Ensure Lombok version compatibility with your Java and Maven versions
2. Add Lombok dependency to `pom.xml`
3. Configure annotation processor path in Maven compiler plugin
4. Replace manual getters/setters with Lombok annotations

## Project Structure

```
src/main/java/com/webforest/
├── controller/         # REST controllers
│   └── EmpController.java
├── entity/            # JPA entities
│   └── EmployeeEntity.java
├── service/           # Business logic layer
│   ├── EmployeeService.java
│   └── EmployeeServiceImpl.java
├── repository/        # Data access layer
│   └── EmployeeRepository.java
├── dto/              # Data transfer objects
│   └── EmployeeDTO.java
├── exception/        # Custom exceptions
└── WebforestApplication.java
```

## Additional Tools

### H2 Console
Access at: `http://localhost:8082/h2-console`
- JDBC URL: `jdbc:mysql://localhost:3306/webforest_db`

### Actuator
Access at: `http://localhost:8082/actuator`

## Configuration

Key application properties (`application.properties`):

```properties
server.port=8082
spring.datasource.url=jdbc:mysql://localhost:3306/webforest_db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Development Notes

- Hibernate 6+ (used by Spring Boot 4.0) uses `MySQLDialect` instead of `MySQL8Dialect`
- The application uses constructor-based dependency injection
- Package structure follows Spring Boot best practices with clear separation of concerns
- Entity scanning is enabled for `com.webforest` base package

## Troubleshooting

### "Not a managed type" Error

If you encounter this error, verify:
1. Entity class is in the `com.webforest.entity` package (lowercase)
2. `@Entity` annotation is present
3. Package declaration matches directory structure (case-sensitive)

### Database Connection Issues

- Ensure MySQL is running on localhost:3306
- Verify database `webforest_db` exists
- Check username/password credentials

### Port Already in Use

If port 8082 is already in use, change it in `application.properties`:
```properties
server.port=8083
```

## License

This project is for demonstration purposes.

## Author

Akash Deep Patel
