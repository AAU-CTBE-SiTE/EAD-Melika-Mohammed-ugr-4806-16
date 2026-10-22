# EAD Labs

## Lab 1: Student Database Operations

A Java console application that connects to MySQL using JDBC. Code is in `src/com/itsc`.

- Connects to MySQL and creates the `StudentsDB` database and `students` table
- Inserts student records and displays five of them
- Updates and deletes a student by ID
- Calculates and displays the average grade
- Requires JDK 17+, MySQL 8.0+ and the MySQL Connector/J driver

## Lab 2: JPA CRUD Operations

A Java console application that performs CRUD operations on MySQL using JPA and Hibernate. Code is in the `lab2` folder.

- Maps a `Student` class (id, name, tech) to a MySQL table that Hibernate creates on the first run
- Creates and reads students with `persist()`, `find()` and a JPQL query
- Updates a student's name and tech, using setters inside a transaction and a JPQL `UPDATE` query
- Deletes a student by ID with `remove()`
- Requires JDK 17+, Maven, MySQL 8.0+ and a database named `jpadb1`