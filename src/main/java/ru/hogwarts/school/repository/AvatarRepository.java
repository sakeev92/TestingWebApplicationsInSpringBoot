package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByStudent(Student student);
}
