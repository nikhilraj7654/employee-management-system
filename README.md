# Employee Management System

A RESTful Employee Management System built with **Java 17, Spring Boot, Spring Data JPA, Hibernate** with a simple web UI.

## Features
- Full CRUD operations for employees (REST API)
- Input validation using Bean Validation (`@NotBlank`, `@Email`, `@NotNull`, `@Positive`)
- Centralized exception handling with `@ControllerAdvice`
- Duplicate email detection (HTTP 409), proper 400 / 404 responses
- Filter employees by department
- Layered architecture: Controller -> Service -> Repository
- H2 in-memory DB by default, MySQL via profile
- Dockerized for deployment

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | List all (optional `?department=`) |
| GET | `/api/employees/{id}` | Get one |
| POST | `/api/employees` | Create |
| PUT | `/api/employees/{id}` | Update |
| DELETE | `/api/employees/{id}` | Delete |

Sample body:
```json
{
  "firstName": "Aarav",
  "lastName": "Sharma",
  "email": "aarav@example.com",
  "department": "Engineering",
  "jobTitle": "Software Engineer",
  "salary": 600000
}
```

## Run locally
```bash
mvn spring-boot:run
```
Open http://localhost:8080

### With MySQL
Edit `application-mysql.properties`, then:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Run with Docker
```bash
docker build -t ems .
docker run -p 8080:8080 ems
```

## Live Demo
(add your Render link here)
