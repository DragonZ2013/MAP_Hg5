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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    CrudRepository<Teacher> tr = Mockito.mock(TeacherSqlRepository.class);
    CrudRepository<Student> sr = Mockito.mock(StudentSqlRepository.class);
    CrudRepository<Course> cr = Mockito.mock(CourseSqlRepository.class);
    Controller controller = null;


    @BeforeEach
    void setUp() throws SQLException, IOException {
        List<Integer> listInt1 = Arrays.asList(1,2,3);
        List<Integer> listInt2 = Arrays.asList(1,3,4);
        List<Integer> listInt3 = Arrays.asList(2,4);
        List<Integer> listInt4 = List.of(2);
        List<Integer> listInt5 = Arrays.asList(1,2);
        List<Integer> listInt6 = Arrays.asList(1,3,4);
        List<Integer> listInt7 = List.of(2);
        List<Integer> listInt8 = Arrays.asList(2,3);
        Course course1 = new Course("course1",1,10, listInt1,30,1);
        Course course2 = new Course("course2",1,25, listInt2,25,2);
        Course course3 = new Course("course3",2,30, listInt3,20,3);
        Course course4 = new Course("course4",3,20, listInt4,15,4);
        Teacher teacher1 = new Teacher ("teacherfirstname1","teacherlastname1",1);
        Teacher teacher2 = new Teacher ("teacherfirstname2","teacherlastname2",2);
        Teacher teacher3 = new Teacher ("teacherfirstname3","teacherlastname3",3);
        Student student1 = new Student("studentfirstname1","studentlastname1",1,55,listInt5);
        Student student2 = new Student("studentfirstname2","studentlastname2",2,65,listInt6);
        Student student3 = new Student("studentfirstname3","studentlastname3",3,25,listInt7);
        Student student4 = new Student("studentfirstname4","studentlastname4",4,45,listInt8);
        List<Course> listCourse1 = Arrays.asList(course1,course2,course3,course4);
        List<Teacher> listTeacher1 = Arrays.asList(teacher1,teacher2,teacher3);
        List<Student> listStudent1 = Arrays.asList(student1,student2,student3,student4);
        Mockito.when(cr.getAll()).thenReturn(listCourse1);
        Mockito.when(tr.getAll()).thenReturn(listTeacher1);
        Mockito.when(sr.getAll()).thenReturn(listStudent1);
        Mockito.when(cr.getObject(1)).thenReturn(course1);
        Mockito.when(cr.getObject(2)).thenReturn(course2);
        Mockito.when(cr.getObject(3)).thenReturn(course3);
        Mockito.when(cr.getObject(4)).thenReturn(course4);
        Mockito.when(tr.getObject(1)).thenReturn(teacher1);
        Mockito.when(tr.getObject(2)).thenReturn(teacher2);
        Mockito.when(tr.getObject(3)).thenReturn(teacher3);
        Mockito.when(sr.getObject(1)).thenReturn(student1);
        Mockito.when(sr.getObject(2)).thenReturn(student2);
        Mockito.when(sr.getObject(3)).thenReturn(student3);
        Mockito.when(sr.getObject(4)).thenReturn(student4);
        controller = new Controller(cr,tr,sr);
    }



    @org.junit.jupiter.api.Test
    void testCourse() throws SQLException, MissingIdException {
        assertEquals(4,controller.listCourses().size());

        assertEquals(1,controller.listCourses().get(0).getCourseId());
        assertEquals(30,controller.listCourses().get(0).getCredits());
        assertEquals(3,controller.listCourses().get(0).getStudentsEnrolled().size());

        assertEquals(2,controller.listCourses().get(1).getCourseId());
        assertEquals(25,controller.listCourses().get(1).getCredits());
        assertEquals(3,controller.listCourses().get(1).getStudentsEnrolled().size());

        assertEquals(3,controller.listCourses().get(2).getCourseId());
        assertEquals(20,controller.listCourses().get(2).getCredits());
        assertEquals(2,controller.listCourses().get(2).getStudentsEnrolled().size());

        assertEquals(4,controller.listCourses().get(3).getCourseId());
        assertEquals(15,controller.listCourses().get(3).getCredits());
        assertEquals(1,controller.listCourses().get(3).getStudentsEnrolled().size());

        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,() -> controller.deleteCourse(5));
        assertEquals("Course with given Id doesn't exist",thrownMissingIdException1.getMessage());

        MissingIdException thrownMissingIdException2 = assertThrows(MissingIdException.class,() -> controller.updateCourse("tempname1",1,10,30,5));
        assertEquals("Course with given Id doesn't exist",thrownMissingIdException2.getMessage());

        MissingIdException thrownMissingIdException3 = assertThrows(MissingIdException.class,() -> controller.updateCourse("tempname2",4,10,30,1));
        assertEquals("Teacher with given Id doesn't exist",thrownMissingIdException3.getMessage());
    }
}