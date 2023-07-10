package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "avatar")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private int age;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "faculty_id")
    private Faculty faculty;
    @OneToOne(mappedBy = "student")
    private Avatar avatar;


    public void setAvatar(Avatar avatar) {
        avatar.setStudent(this);
        this.avatar = avatar;
    }
}
