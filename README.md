AI chat history: https://claude.ai/share/459a9e93-45d5-4a44-ba69-667b95511a5f

# Student API

A RESTful CRUD API for managing students, built with Spring Boot, Spring Data JPA and MySQL.
Written for the AI-guided Spring Boot lab (AAU, SiTE).

## Stack

Java 17, Spring Boot 4.1.2, Spring Data JPA, MySQL 8, Lombok, Maven

## Structure

    com.craft.studentapi
      model/Student.java
      repository/StudentRepository.java
      service/StudentService.java
      controller/StudentController.java

## Run

1. Create the database: `CREATE DATABASE student_db;`
2. Set your MySQL password in `src/main/resources/application.properties`.
3. Run `StudentApiApplication` in IntelliJ. The app starts on http://localhost:8080.

## Endpoints

| Operation         | Method | URL                             |
|-------------------|--------|---------------------------------|
| Create            | POST   | /api/students                   |
| Get all           | GET    | /api/students                   |
| Get one           | GET    | /api/students/{id}              |
| Get by department | GET    | /api/students/department/{name} |
| Update            | PUT    | /api/students/{id}              |
| Delete            | DELETE | /api/students/{id}              |

Example body for POST and PUT:

    {"firstName":"Abel","lastName":"Tesfaye","email":"abel@aau.edu.et","department":"IT"}

## Notes

- No DTOs, no ResponseEntity, no exception handling (lab rules). A missing student returns null with 200 OK.
- email is unique.

## AI history

See [AI_CHAT_HISTORY.md](AI_CHAT_HISTORY.md).

