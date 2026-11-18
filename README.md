# Lab 7: Actor Management with MySQL (Spring Boot CRUD REST API)

A Spring Boot application that stores actors in a MySQL database and exposes a REST API for create, read, update, delete and search.

## Structure

- `model/Actor` - the JPA entity mapped to the `actors` table
- `repo/ActorRepo` - Spring Data JPA repository
- `service/ActorService` - business logic and 404 handling
- `controller/ActorController` - REST endpoints under `/api/actors`

## Setup

1. Create the database in MySQL: `CREATE DATABASE actor_db;`
2. Set your MySQL username and password in `src/main/resources/application.properties`.
3. Run `ActorManagementApplication`. The server starts on port 8081 and Hibernate creates the `actors` table.

## Endpoints

| Method | URL | Description |
|---|---|---|
| POST | /api/actors | Create an actor |
| GET | /api/actors | List all actors |
| GET | /api/actors/{id} | Get one actor |
| GET | /api/actors/search?name=tom | Search by name |
| PUT | /api/actors/{id} | Update an actor |
| DELETE | /api/actors/{id} | Delete an actor |

Example body for POST and PUT:

```json
{"name": "Tom Hanks", "age": 68, "country": "USA"}
```
