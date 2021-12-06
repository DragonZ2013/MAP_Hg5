package Main.Controller;

import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Person;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CrudRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Controller {
    private CrudRepository<Teacher> tr;
    private CrudRepository<Course> cr;
    private CrudRepository<Student> sr;

    /**
     * Constructor for controller objects
     * @param cr
     * @param tr
     * @param sr
     * @throws IOException
     */
    public Controller(CrudRepository<Course> cr, CrudRepository<Teacher> tr, CrudRepository<Student> sr) throws IOException {
        this.cr = cr;
        this.tr = tr;
        this.sr = sr;
    }

    /**
     * adds a teacher object to the TeacherRepository
     * @param firstName
     * @param lastName
     * @param teacherId
     * @throws ExistentIdException
     */
    public void createTeacher(String firstName,String lastName,int teacherId) throws ExistentIdException, SQLException {
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                throw new ExistentIdException("Teacher Id is already in array");
        Teacher t = new Teacher(firstName,lastName,teacherId);
        tr.create(t);
    }

    /**
     * adds a student object to the StudentRepository
     * @param firstName
     * @param lastName
     * @param studentId
     * @param totalCredits
     * @throws ExistentIdException
     */
    public void createStudent(String firstName,String lastName,int studentId,int totalCredits) throws ExistentIdException, SQLException {
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                throw new ExistentIdException("Student Id is already in array");
        Student s = new Student(firstName,lastName,studentId,totalCredits,new ArrayList<>());
        sr.create(s);

    }

    /**
     * adds a course object to the CourseRepository
     * @param name
     * @param teacherId
     * @param maxEnrollment
     * @param credits
     * @param courseId
     * @throws ExistentIdException
     * @throws MissingIdException
     */
    public void createCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws ExistentIdException, MissingIdException, SQLException {
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                throw new ExistentIdException("Course Id is already in array");
        Teacher teacher = null;
        teacher = tr.getObject(teacherId);
        if(teacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");

        Course c = new Course(name,teacherId,maxEnrollment,new ArrayList<>(),credits,courseId);
        cr.create(c);
    }


    /**
     * modifies a teacher object from the TeacherRepository
     * @param firstName
     * @param lastName
     * @param teacherId
     * @throws MissingIdException
     */
    public void updateTeacher(String firstName,String lastName,int teacherId) throws MissingIdException, SQLException {
        Teacher teacher = null;
        teacher = tr.getObject(teacherId);
        if(teacher == null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        Teacher t = new Teacher(firstName,lastName,teacherId);
        tr.update(t);
    }

    /**
     * Modifies a student object from the StudentRepository
     * @param firstName
     * @param lastName
     * @param studentId
     * @param totalCredits
     * @throws MissingIdException
     */
    public void updateStudent(String firstName,String lastName,int studentId,int totalCredits) throws MissingIdException, SQLException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student ==null)
        {
            throw new MissingIdException("Student with given Id doesn't exist");
        }
        Student s = new Student(firstName,lastName,studentId,totalCredits, student.getEnrolledCourses());
        sr.update(s);

    }

    /**
     * Modifies a course object from the CourseRepository
     * @param name
     * @param teacherId
     * @param maxEnrollment
     * @param credits
     * @param courseId
     * @throws MissingIdException
     * @throws MaxSizeException
     */
    public void updateCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws MissingIdException, MaxSizeException, SQLException {
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        Teacher teacher = null;
        for(Teacher t:tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher=t;
        if(teacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        if(maxEnrollment<course.getStudentsEnrolled().size())
            throw new MaxSizeException("Student array has more elements than new Max Enrollment");

        Course c = new Course(name,teacherId,maxEnrollment,course.getStudentsEnrolled(),credits,courseId);
        cr.update(c);
    }

    /**
     * removes a course object from the CourseRepository
     * @param courseId
     * @throws MissingIdException
     */
    public void deleteCourse(int courseId) throws MissingIdException, SQLException {

        Course tempCourse = cr.getObject(courseId);
        if(tempCourse==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        cr.delete(courseId);
    }

    /**
     * removes a teacher object from the teacher repository
     * @param teacherId
     * @throws MissingIdException
     */
    public void deleteTeacher(int teacherId) throws MissingIdException, SQLException {
        Teacher tempTeacher = tr.getObject(teacherId);
        if(tempTeacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        cr.delete(teacherId);
    }

    /**
     * removes a student object from the StudentRepository
     * @param studentId
     * @throws MissingIdException
     */
    public void deleteStudent(int studentId) throws MissingIdException, SQLException {
        Student tempStudent = sr.getObject(studentId);
        if(tempStudent==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        cr.delete(studentId);
    }

    /**
     * registers a student to a course
     * @param studentId
     * @param courseId
     * @throws MissingIdException
     * @throws MaxSizeException
     */
    public void registerStudent(int studentId,int courseId) throws MissingIdException, MaxSizeException, SQLException, ExistentIdException {
        Student student = sr.getObject(studentId);
        if(student ==null)
            throw new MissingIdException("Student with given Id doesn't exist");
        Course course = cr.getObject(courseId);
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        if(course.getMaxEnrollment()==course.getStudentsEnrolled().size())
            throw new MaxSizeException("Course already hax maximum number of students enrolled");
        if(student.getEnrolledCourses().contains(courseId))
            throw new ExistentIdException("Student already enrolled to given course");
        student.setTotalCredits(student.getTotalCredits()+course.getCredits());
        student.getEnrolledCourses().add(courseId);
        sr.update(student);



    }
    public CrudRepository<Course> getCr() {
        return cr;
    }

    public void setCr(CrudRepository<Course> cr) {
        this.cr = cr;
    }

    public CrudRepository<Teacher> getTr() {
        return tr;
    }

    public void setTr(CrudRepository<Teacher> tr) {
        this.tr = tr;
    }

    public CrudRepository<Student> getSr() {
        return sr;
    }

    public void setSr(CrudRepository<Student> sr) {
        this.sr = sr;
    }


    /**
     * Returns the list of students sorted by their LastName - WIP - FirstName sorting, thenComparing breaks
     * @return retList List<Student>
     */
    public List<Student> sortStudents() throws SQLException {
        List<Student> studentList = sr.getAll();
        Comparator<Person> studentComparator = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName);
        return studentList.stream().sorted(studentComparator).toList();
    }

    /**
     * Returns the list of students with more than minCredits credits
     * @param minCredits
     * @return retList List<Student>
     */
    public List<Student> filterStudents(int minCredits) throws SQLException {
        List<Student> studentList = sr.getAll();

        return studentList.stream().filter(o->o.getTotalCredits()>=minCredits).toList();
    }

    /**
     * Returns the list of courses sorted by their credits
     * @return retList List<Course>
     */
    public List<Course> sortCourses() throws SQLException {
        List<Course> courseList = cr.getAll();
        Comparator<Course> courseComparator = Comparator.comparing(Course::getCredits);
        return courseList.stream().sorted(courseComparator).toList();
    }

    /**
     * Returns the list of courses with more than minCreds credits
     * @param minCredits
     * @return retList List<Course>
     */
    public List<Course> filterCourses(int minCredits) throws SQLException {
        List<Course> courseList = cr.getAll();
        return courseList.stream().filter(o->o.getCredits()>=minCredits).toList();
    }

    /**
     * Saves all repositories in their current state
     * @throws IOException
     */




}
