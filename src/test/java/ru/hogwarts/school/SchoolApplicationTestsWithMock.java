package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SchoolApplicationTestsWithMock {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService service;
    @InjectMocks
    private StudentController controller;
    static Long id = 1L;
    static String name = "First";
    static int age = 2;
    static Student student1 = new Student();
    static Student student2 = new Student();

    @BeforeAll
    public static void setup()
    {
        student1.setId(id);
        student1.setName(name);
        student1.setAge(age);

        student2.setId(id + 1);
        student2.setName(name);
        student2.setAge(age);
    }


    @Test
    @DisplayName("Проверка GET метода getById")
    public void test2() throws Exception {

        when(studentRepository.getById(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/{id}", id))
                .andExpect(status().isNotFound());


        when(studentRepository.getById(any())).thenReturn(student1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    @DisplayName("Проверка GET метода getByAge")
    public void test3() throws Exception {

        List<Student> testList = List.of(student1, student2);

        when(studentRepository.findByAge(age)).thenReturn(testList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/sortByAge/{age}", age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(id))
                .andExpect(jsonPath("$.[0].name").value(name))
                .andExpect(jsonPath("$.[0].age").value(age))
                .andExpect(jsonPath("$.[1].id").value(id+1))
                .andExpect(jsonPath("$.[1].name").value(name))
                .andExpect(jsonPath("$.[1].age").value(age));

    }

    @Test
    @DisplayName("Проверка GET метода getByAgeBetween")
    public void test4() throws Exception {

        List<Student> testList = List.of(student1);

        when(studentRepository.findByAgeBetween(anyInt(),anyInt())).thenReturn(testList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findByAgeBetween?ageMin=1&ageMax=3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(id))
                .andExpect(jsonPath("$.[0].name").value(name))
                .andExpect(jsonPath("$.[0].age").value(age));

    }

    @Test
    @DisplayName("Проверка GET метода getById")
    public void test5() throws Exception {
        student1.setFaculty(new Faculty("Black","First"));

        when(studentRepository.getById(any())).thenReturn(student1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getFacultyByIdStudent/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("First"))
                .andExpect(jsonPath("$.color").value("Black"));

    }

    @Test
    @DisplayName("Проверка GET метода getById")
    public void test6() throws Exception {

        List<Student> testList = List.of(student1);

        when(studentRepository.findAll()).thenReturn(testList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(id))
                .andExpect(jsonPath("$.[0].name").value(name))
                .andExpect(jsonPath("$.[0].age").value(age));

    }
}
