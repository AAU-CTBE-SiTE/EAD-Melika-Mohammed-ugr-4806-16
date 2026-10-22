\# Lab 2: JPA CRUD Operations



A Java console application that performs CRUD operations on MySQL using JPA and Hibernate.



\- Maps a `Student` class (id, name, tech) to a MySQL table that Hibernate creates on the first run

\- Creates and reads students with `persist()`, `find()` and a JPQL query

\- Updates a student's name and tech, using setters inside a transaction and a JPQL `UPDATE` query

\- Deletes a student by ID with `remove()`

\- Requires JDK 17+, Maven, MySQL 8.0+ and a database named `jpadb1`

