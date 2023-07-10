package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Faculty faculty = service.getById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping()
    public ResponseEntity createFaculty(@RequestBody Faculty faculty) {
        Faculty value = service.add(faculty);
        return ResponseEntity.ok(value);
    }

    @PutMapping()
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {
        Faculty value = service.change(faculty);
        return ResponseEntity.ok(value);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity deleteFaculty(@PathVariable Long facultyId) {
        service.remove(facultyId);
        return ResponseEntity.ok("Факультет удален");
    }
    @DeleteMapping("/removeAll")
    public ResponseEntity removeAll() {
        service.removeAll();
        return ResponseEntity.ok("Факультеты удалены");
    }

    @GetMapping("/getByColor/{color}")
    public ResponseEntity getByColor(@PathVariable String color) {
        return ResponseEntity.ok(service.getByColor(color));
    }
    @GetMapping("/findByNameOrColor")
    public ResponseEntity findByNameOrColor(String name, String color) {
        if(name == null && color == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.findByNameOrColor(name,color));
    }
    @GetMapping("/getAllStudents/{facultyId}")
    public List<Student> getAllStudentsByFaculty(@PathVariable Long facultyId)
    {
        return service.getAllStudentsByFaculty(facultyId);
    }

}
