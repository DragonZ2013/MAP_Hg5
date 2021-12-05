package Main.Controller;

import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Student;
import Main.Repository.CourseFileRepository;
import Main.Repository.StudentFileRepository;
import Main.Repository.TeacherFileRepository;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {


    /*@BeforeEach
    void setup() throws IOException {
        TeacherRepository tr = new TeacherRepository("TeacherDataTest.json");
        CourseRepository cr = new CourseRepository(tr,"CourseDataTest.json");
        StudentRepository sr = new StudentRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
    }*/

    @org.junit.jupiter.api.Test
    void updateTeacher() throws IOException, MissingIdException {
        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        controller.updateTeacher("NewFirstName","NewLastName",1);
        for (Course c:controller.getCr().getAll()) {
            assertEquals(c.getTeacher().getFirstName(),"NewFirstName");
            assertEquals(c.getTeacher().getLastName(),"NewLastName");
        }
        try {
            controller.updateTeacher("test1", "test1", 2);
            assert(false);
        }
        catch (MissingIdException e){
            assert(true);
        }
    }

    @org.junit.jupiter.api.Test
    void updateStudent() throws IOException, MissingIdException {
        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        assertThrows(MissingIdException.class,()->controller.updateStudent("NewFirstName","NewLastName",6,0));

    }

    @org.junit.jupiter.api.Test
    void updateCourse() throws IOException {
        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.updateCourse("NewName",1,10,40,6);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }

        try {
            controller.updateCourse("NewName",2,10,40,1);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        try {
            controller.updateCourse("NewName",1,-1,40,1);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        try {
            controller.updateCourse("NewName",1,10,40,1);
            assert(true);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(false);
        }
    }

    @org.junit.jupiter.api.Test
    void deleteCourse() throws IOException {
        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.deleteCourse(5);
            assert(false);
        }
        catch (MissingIdException e){
            assert(true);
        }
        try {
            controller.deleteStudent(1);
            assert(true);
        }
        catch (MissingIdException e){
            assert(false);
        }
    }

    @org.junit.jupiter.api.Test
    void deleteTeacher() throws IOException {
        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.deleteTeacher(2);
            assert(false);
        }
        catch (MissingIdException e){
            assert(true);
        }
        try {
            controller.deleteTeacher(1);
            assert(true);
        }
        catch (MissingIdException e){
            assert(false);
        }
        assert(controller.getCr().getAll().size()==0);

    }

    @org.junit.jupiter.api.Test
    void deleteStudent() throws IOException {
        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.deleteStudent(5);
            assert(false);
        }
        catch (MissingIdException e){
            assert(true);
        }
        try {
            controller.deleteStudent(1);
            assert(true);
        }
        catch (MissingIdException e){
            assert(false);
        }
    }

    @org.junit.jupiter.api.Test
    void registerStudent() throws IOException, MaxSizeException, MissingIdException {

        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.registerStudent(6,1);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        try {
            controller.registerStudent(1,7);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        controller.updateCourse("NewName",1,1,40,2);
        try {
            controller.registerStudent(1,2);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        try {
            controller.registerStudent(1,1);
            assert(true);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(false);
        }
    }

    @org.junit.jupiter.api.Test
    void sortStudents() throws IOException {

        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        List<Student> sortedStudentList = controller.sortStudents();
        assert(sortedStudentList.get(0).getStudentId()==2);
        assert(sortedStudentList.get(1).getStudentId()==3);
        assert(sortedStudentList.get(2).getStudentId()==1);

    }

    @org.junit.jupiter.api.Test
    void filterStudents() throws IOException {

        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        assert(controller.filterStudents(25).size()==1);
    }

    @org.junit.jupiter.api.Test
    void sortCourses() throws IOException {

        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        List<Course> sortedCourseList= controller.sortCourses();
        assert(sortedCourseList.get(0).getCourseId()==3);
        assert(sortedCourseList.get(1).getCourseId()==1);
        assert(sortedCourseList.get(2).getCourseId()==2);
    }

    @org.junit.jupiter.api.Test
    void filterCourses() throws IOException {

        TeacherFileRepository tr = new TeacherFileRepository("TeacherDataTest.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseDataTest.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        assert (controller.filterCourses(25).size()==1);
    }
}