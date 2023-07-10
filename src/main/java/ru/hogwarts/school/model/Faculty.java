package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@ToString(exclude = "students")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private List<Student> students;

    public Faculty() {
    }

    public Faculty(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public void addStudent(Student value) {
        this.students.add(value);
        value.setFaculty(this);
    }

    public void removeStudent(Student value) {
        this.students.remove(value);
    }
}
