package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String filePath;
    long fileSize;
    String mediaType;
    byte[] data;
    @OneToOne()
    @JoinColumn(name = "student_id")
    Student student;
}
