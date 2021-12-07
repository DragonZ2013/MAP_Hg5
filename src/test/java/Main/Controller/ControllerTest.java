package Main.Controller;

import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseSqlRepository;
import Main.Repository.CrudRepository;
import Main.Repository.StudentSqlRepository;
import Main.Repository.TeacherSqlRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {



    @BeforeEach
    void setup() throws IOException {
        CrudRepository<Teacher> tr = Mockito.mock(TeacherSqlRepository.class);
        CrudRepository<Student> sr = Mockito.mock(StudentSqlRepository.class);
        CrudRepository<Course> cr = Mockito.mock(CourseSqlRepository.class);
        List<Integer> list1 = Arrays.asList(1,2,3);
        Course course1 = new Course("course1",1,10, list1,20,1);
    }



    @org.junit.jupiter.api.Test
    void testCourseGet(){

    }
}