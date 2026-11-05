package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Student student1 = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);

        System.out.println(student1);
        student1.getDetails();

        // Student is prototype: each getBean creates a new Student
        System.out.println("Same Student? " + (student1 == student2));

        // Address is singleton: both Students share one Address
        System.out.println("Same Address? " + (student1.getAddress() == student2.getAddress()));
    }
}