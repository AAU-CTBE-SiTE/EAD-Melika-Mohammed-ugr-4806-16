package com.craft;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ToString
@Component
@Scope("prototype")
public class Student {
    private int id;
    private String name;
    private int age;

    private Address address;

    private List<Subject> subjects;

    // Constructor injection: literal values and the Address object
    @Autowired
    public Student(@Value("101") int id,
                   @Value("John Doe") String name,
                   @Value("20") int age,
                   Address address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        System.out.println("Student Parameterized Constructor Called");
    }

    // Setter injection: Spring passes in every Subject bean as a list
    @Autowired
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        System.out.println("setSubjects Called");
    }

    public void getDetails() {
        System.out.println("Student Name: " + this.getName());

        int index = 1;
        for (Subject subject : subjects) {
            System.out.println("Subject " + index + ": " + subject.getSubjectName());
            index++;
        }
    }
}