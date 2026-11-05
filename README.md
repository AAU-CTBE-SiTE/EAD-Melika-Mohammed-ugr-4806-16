# Lab 4: Spring Core with Annotations

The Spring Core lab from lab3, rewritten with annotations instead of `beansConfig.xml`. `AppConfig` turns on component scanning, and Spring builds every object from the annotations on each class.

- Bean creation: `Address` and `Student` are marked `@Component`, and `AppConfig` uses `@ComponentScan("com.craft")` to find them
- Literal injection: `@Value` fills in the address fields and the student id, name and age
- Constructor injection: `Student` has an `@Autowired` constructor, and Spring passes the `Address` bean into it
- Setter and collection injection: `@Autowired setSubjects(List<Subject>)` receives both `Subject` beans, which are defined as `@Bean` methods in `AppConfig`
- Bean scopes: `Student` is `@Scope("prototype")`, so each `getBean` creates a new one, while `Address` stays a singleton shared by every Student
- Requires JDK 17+ and Maven (Spring Context 6.2.7, Lombok 1.18.38)

## Output

```
Address Constructor Called
Student Parameterized Constructor Called
setSubjects Called
Student Parameterized Constructor Called
setSubjects Called
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=[Subject(subjectName=Maths 101), Subject(subjectName=Java EE)])
Student Name: John Doe
Subject 1: Maths 101
Subject 2: Java EE
Same Student? false
Same Address? true
```
