package Main.Controller;

import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Person;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Controller {
    private TeacherRepository tr;
    private CourseRepository cr;
    private StudentRepository sr;

    /**
     * Constructor for controller objects
     * @param cr
     * @param tr
     * @param sr
     * @throws IOException
     */
    public Controller(CourseRepository cr, TeacherRepository tr, StudentRepository sr) throws IOException {
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
    public void createTeacher(String firstName,String lastName,int teacherId) throws ExistentIdException {
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                throw new ExistentIdException("Teacher Id is already in array");
        Teacher t = new Teacher(firstName,lastName,new ArrayList<>(),teacherId);
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
    public void createStudent(String firstName,String lastName,int studentId,int totalCredits) throws ExistentIdException {
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
    public void createCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws ExistentIdException, MissingIdException {
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                throw new ExistentIdException("Course Id is already in array");
        Teacher teacher = null;
        for(Teacher t:tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher=t;
        if(teacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");

        Course c = new Course(name,teacher,maxEnrollment,new ArrayList<>(),credits,courseId);

        cr.create(c);
        teacher.getCourses().add(c);
        tr.update(teacher);
    }


    /**
     * modifies a teacher object from the TeacherRepository
     * @param firstName
     * @param lastName
     * @param teacherId
     * @throws MissingIdException
     */
    public void updateTeacher(String firstName,String lastName,int teacherId) throws MissingIdException {
        Teacher teacher = null;
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher = t;
        if(teacher == null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        Teacher t = new Teacher(firstName,lastName,teacher.getCourses(),teacherId);
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
    public void updateStudent(String firstName,String lastName,int studentId,int totalCredits) throws MissingIdException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student==null)
        {
            throw new MissingIdException("Student with given Id doesn't exist");
        }
        Student s = new Student(firstName,lastName,studentId,totalCredits,student.getEnrolledCourses());
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
    public void updateCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws MissingIdException, MaxSizeException {
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
        Teacher t = course.getTeacher();
        t.getCourses().removeIf(teach->teach.getCourseId()==courseId);
        tr.update(t);
        Course c = new Course(name,teacher,maxEnrollment,course.getStudentsEnrolled(),credits,courseId);
        teacher.getCourses().add(course);
        Teacher teacherRet = new Teacher(teacher.getFirstName(),teacher.getLastName(),teacher.getCourses(),teacherId);
        tr.update(teacherRet);
        cr.update(c);
    }

    /**
     * removes a course object from the CourseRepository
     * @param courseId
     * @throws MissingIdException
     */
    public void deleteCourse(int courseId) throws MissingIdException {
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        for(Student s:course.getStudentsEnrolled()){
            s.getEnrolledCourses().removeIf(o->o.getCourseId()==courseId);
            sr.update(s);
        }
        cr.delete(course);
    }

    /**
     * removes a teacher object from the teacher repository
     * @param teacherId
     * @throws MissingIdException
     */
    public void deleteTeacher(int teacherId) throws MissingIdException {
        Teacher teacher = null;
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher = t;
        if(teacher == null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        for(Course c:teacher.getCourses())
            this.deleteCourse(c.getCourseId());
        tr.delete(teacher);
    }

    /**
     * removes a student object from the StudentRepository
     * @param studentId
     * @throws MissingIdException
     */
    public void deleteStudent(int studentId) throws MissingIdException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student==null)
            throw new MissingIdException("Student with given Id doesn't exist");
        for(Course c:student.getEnrolledCourses()){
            c.getStudentsEnrolled().removeIf(o->o.getStudentId()==studentId);
            cr.update(c);
        }

        sr.delete(student);
    }

    /**
     * registers a student to a course
     * @param studentId
     * @param courseId
     * @throws MissingIdException
     * @throws MaxSizeException
     */
    public void registerStudent(int studentId,int courseId) throws MissingIdException, MaxSizeException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student==null)
            throw new MissingIdException("Student with given Id doesn't exist");
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        if(course.getMaxEnrollment()==course.getStudentsEnrolled().size())
            throw new MaxSizeException("Course already hax maximum number of students enrolled");
        student.setTotalCredits(student.getTotalCredits()+course.getCredits());
        student.getEnrolledCourses().add(course);
        sr.update(student);
        course.getStudentsEnrolled().add(student);
        cr.update(course);



    }
    public CourseRepository getCr() {
        return cr;
    }

    public void setCr(CourseRepository cr) {
        this.cr = cr;
    }

    public TeacherRepository getTr() {
        return tr;
    }

    public void setTr(TeacherRepository tr) {
        this.tr = tr;
    }

    public StudentRepository getSr() {
        return sr;
    }

    public void setSr(StudentRepository sr) {
        this.sr = sr;
    }


    /**
     * Returns the list of students sorted by their LastName - WIP - FirstName sorting, thenComparing breaks
     * @return retList List<Student>
     */
    public List<Student> sortStudents(){
        List<Student> studentList = sr.getAll();
        Comparator<Student> studentComparator = Comparator.comparing(Person::getLastName);//.thenComparing(o -> o.getFirstName());
        return studentList.stream().sorted(studentComparator).toList();
    }

    /**
     * Returns the list of students with more than minCredits credits
     * @param minCredits
     * @return retList List<Student>
     */
    public List<Student> filterStudents(int minCredits){
        List<Student> studentList = sr.getAll();

        return studentList.stream().filter(o->o.getTotalCredits()>=minCredits).toList();
    }

    /**
     * Returns the list of courses sorted by their credits
     * @return retList List<Course>
     */
    public List<Course> sortCourses(){
        List<Course> courseList = cr.getAll();
        Comparator<Course> courseComparator = Comparator.comparing(o -> Integer.valueOf(o.getCredits()));
        return courseList.stream().sorted(courseComparator).toList();
    }

    /**
     * Returns the list of courses with more than minCreds credits
     * @param minCredits
     * @return retList List<Course>
     */
    public List<Course> filterCourses(int minCredits){
        List<Course> courseList = cr.getAll();
        return courseList.stream().filter(o->o.getCredits()>=minCredits).toList();
    }

    /**
     * Saves all repositories in their current state
     * @throws IOException
     */
    public void SaveAll() throws IOException {
        sr.close("StudentData.json");
        cr.close("CourseData.json");
        tr.close("TeacherData.json");
    }



}
