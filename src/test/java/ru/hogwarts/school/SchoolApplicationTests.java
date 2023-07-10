package ru.hogwarts.school;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final Faculty TestFacultyModel = new Faculty("Black", "First");


    @Test
    void contextLoads() throws Exception
    {
        assertNotNull(facultyController);
    }
    @Test
    @DisplayName("Проверка POST метода add")
    public void test1(){
        assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/school/faculty", TestFacultyModel ,String.class));
    }
    @Test
    @DisplayName("Проверка DELETE метода deleteFaculty")
    public void test2(){
        ResponseEntity resp = this.restTemplate.exchange("http://localhost:" + port + "/school/faculty/1", HttpMethod.DELETE,null, Void.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    @DisplayName("Проверка DELETE метода removeAll")
    public void test3(){
        ResponseEntity resp = this.restTemplate.exchange("http://localhost:" + port + "/school/faculty/removeAll", HttpMethod.DELETE,null, Void.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }
    @Test
    @DisplayName("Прверка GET метода getByColor")
    public void test4()
    {
        assertNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/getByColor/Test" ,String.class));

        this.restTemplate.postForObject("http://localhost:" + port + "/school/faculty", TestFacultyModel ,String.class);

        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/getByColor/Black" ,String.class));

        this.restTemplate.delete("http://localhost:" + port + "/school/faculty/1");
    }

    @Test
    @DisplayName("Проверка GET метода FindByNameOrColor")
    public void test5()
    {
        assertNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/findByNameOrColor?name=Test" ,String.class));
        assertNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/findByNameOrColor?color=Test" ,String.class));
        assertNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/findByNameOrColor?name=Test&color=Test" ,String.class));

        this.restTemplate.postForObject("http://localhost:" + port + "/school/faculty", TestFacultyModel ,String.class);

        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/findByNameOrColor?name=First" ,String.class));
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/findByNameOrColor?color=Black" ,String.class));
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/findByNameOrColor?name=First&color=Black" ,String.class));

        this.restTemplate.delete("http://localhost:" + port + "/school/faculty/1");
    }

    @Test
    @DisplayName("Прверка GET метода get")
    public void test6()
    {
        ResponseEntity resp = this.restTemplate.exchange("http://localhost:" + port + "/school/faculty/get/1", HttpMethod.GET,null, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());

        this.restTemplate.postForObject("http://localhost:" + port + "/school/faculty", TestFacultyModel ,String.class);

        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/get/1" ,String.class));

        this.restTemplate.delete("http://localhost:" + port + "/school/faculty/1");
    }

    @Test
    @DisplayName("Прверка GET метода getAll")
    public void test7()
    {


        this.restTemplate.postForObject("http://localhost:" + port + "/school/faculty", TestFacultyModel ,String.class);

        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/school/faculty/getAll" ,String.class));

        this.restTemplate.delete("http://localhost:" + port + "/school/faculty/1");
    }




}
