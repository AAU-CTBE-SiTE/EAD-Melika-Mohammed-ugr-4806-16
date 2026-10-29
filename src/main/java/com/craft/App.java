package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");
        Student student1 = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);

        // Check if both references point to the SAME instance
        System.out.println(student1 == student2);

        // Modify student1's name
        student1.setName("John Doe");

        // Check student2
        System.out.println(student2.getName());
    }
}