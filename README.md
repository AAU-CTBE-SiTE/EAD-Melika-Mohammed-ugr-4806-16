# Lab 4: Constructor Injection with Annotations

A Spring Core console application where the IoC container creates a `Student` and passes an `Address` into its constructor. Everything is configured with annotations, so there is no `beansConfig.xml`.

- `AppConfig` is marked `@Configuration` and `@ComponentScan("com.craft")`, so Spring finds every class in the package marked `@Component`
- `Address` is a `@Component`, and `@Value` fills in its `city` and `country` fields
- `Student` is a `@Component` with an `@Autowired` constructor: `@Value` supplies the id, name and age, and Spring passes in the `Address` bean by its type
- `App` starts the container with `AnnotationConfigApplicationContext(AppConfig.class)` and gets the finished `Student` with `getBean`
- Requires JDK 17+ and Maven (Spring Context 6.2.7, Lombok 1.18.38)

## Output

```
Address Constructor Called
Student Parameterized Constructor Called
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia))
```
