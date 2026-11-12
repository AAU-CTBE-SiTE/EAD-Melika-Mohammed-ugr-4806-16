# Lab 6 - Spring AOP

A Spring Boot REST API for managing actors (from Lab 5), extended with Aspect-Oriented Programming. Cross-cutting concerns such as logging, timing and auditing are written once in aspects instead of inside the controller.

## Run

Open the project in IntelliJ IDEA and run `Lab5Application.java`. The API starts at `http://localhost:8080/actors`. Aspect output appears in the console.

## Dependency

`spring-boot-starter-aspectj` (the Spring Boot 4 name of the former `spring-boot-starter-aop`).

## Aspects (package `com.site.lab5.aop`)

| Class | Advice | Purpose |
|---|---|---|
| `LoggingAspect` | `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After` | Logs every controller call, its result and any exception |
| `TimingAspect` | `@Around` | Measures execution time of methods marked `@LogExecutionTime` |
| `AuditAspect` | `@Before` with `args()` | Audits delete requests using the bound id argument |
| `AdvancedAspect` | `@Before`, `@Around`, `@DeclareParents` | Shows `within`, `target`, `bean` pointcuts, annotation attributes, argument modification and introductions |
| `CommonPointcuts` | `@Pointcut` | Reusable pointcut expressions |

Custom annotations: `@LogExecutionTime` and `@Audited`.

## Endpoints

| Method | Endpoint | Function |
|---|---|---|
| GET | `/actors` | Get all actors |
| GET | `/actors/{id}` | Get one actor |
| POST | `/actors` | Add an actor |
| PUT | `/actors/{id}` | Update an actor |
| DELETE | `/actors/{id}` | Delete an actor |
| GET | `/actors/error-test` | Throws an exception to demonstrate `@AfterThrowing` |

Data is stored in memory and resets when the application restarts.
