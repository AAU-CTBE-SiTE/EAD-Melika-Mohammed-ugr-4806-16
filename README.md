# Lab 5 - Spring Boot CRUD REST API

A REST API for managing actors, built with Spring Boot. Data is kept in an in-memory `ArrayList`, so there is no database.

## Run

Open the project in IntelliJ IDEA and run `Lab5Application.java`. The API starts at `http://localhost:8080/actors`.

## Endpoints

| Method | Endpoint | Function |
|---|---|---|
| GET | `/actors` | Get all actors |
| GET | `/actors/{id}` | Get one actor |
| POST | `/actors` | Add an actor |
| PUT | `/actors/{id}` | Update an actor |
| DELETE | `/actors/{id}` | Delete an actor |

Data resets when the application restarts.
