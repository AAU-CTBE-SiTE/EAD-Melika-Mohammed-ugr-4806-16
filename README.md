# Lab 3: Spring Core (IoC and Dependency Injection)

A Java console application where the Spring IoC container creates and wires the objects, configured in `beansConfig.xml`. Each part of the lab is its own commit, so the commit history shows the code at every stage.

- Moves creation of `Student` from `new Student()` to Spring, using `ClassPathXmlApplicationContext`
- Injects values through setters (`<property>`) and through a constructor (`<constructor-arg>` by name, position and index)
- Injects an `Address` bean with `ref`, and a collection of `Subject` beans with `<list>`, `<array>` and `<set>`
- Compares singleton and prototype bean scopes
- Requires JDK 17+ and Maven (Spring Context 6.2.7, Lombok 1.18.38)
