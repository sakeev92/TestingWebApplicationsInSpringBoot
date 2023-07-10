package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;


@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Student student = service.getById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping()
    public ResponseEntity createStudent(@RequestBody Student student) {
        Student value = service.add(student);
        return ResponseEntity.ok(value);
    }

    @PutMapping()
    public ResponseEntity updateStudent(@RequestBody Student student) {
        Student value = service.change(student);
        return ResponseEntity.ok(value);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        service.remove(studentId);
        return ResponseEntity.ok("Ученик удален");
    }
    @DeleteMapping("/removeAll")
    public ResponseEntity removeAll() {
        service.removeAll();
        return ResponseEntity.ok("Ученики удалены");
    }

    @GetMapping("/sortByAge/{age}")
    public ResponseEntity getByAge(@PathVariable int age) {
        return ResponseEntity.ok(service.getByAge(age));
    }
    @GetMapping("/findByAgeBetween")
    public ResponseEntity findByAgeBetween(@RequestParam int ageMin, @RequestParam int ageMax) {
        return ResponseEntity.ok(service.findByAgeBetween(ageMin,ageMax));
    }
    @GetMapping("/getFacultyByIdStudent/{id}")
    public ResponseEntity getFacultyByIdStudent(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.getFacultyByIdStudent(id));
    }


}
