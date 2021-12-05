package Main.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher extends Person{
    private List<Student> courses;
    private int teacherId;


    /**
     * Compares the parameters of 2 Course objects
     * @param o
     * @return true if the parameters are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return teacherId == teacher.teacherId && Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courses, teacherId);
    }

    /**
     * Conversion to String for Teacher objects
     * @return String
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "courses=" + this.coursesToId() +
                ", teacherId=" + teacherId +
                ", firstName=" + this.getFirstName()+
                ", lastName=" + this.getLastName()+
                '}';
    }

    /**
     * Converts the course array to an int array, used to avoid recursive printing
     * @return List<Integer>
     */
    public List<Student> coursesToId(){
        List<Student> retList = new ArrayList<>();
        for(Student c : this.courses)
            retList.add(c.getCourseId());
        return retList;
    }

    /**
     * getter for Teacher.teacherId
     * @return int
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * setter for Teacher.teacherId
     * @param teacherId
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * Constructor for Teacher objects
     * @param firstName
     * @param lastName
     * @param courses
     * @param teacherId
     */
    public Teacher(String firstName, String lastName, List<Student> courses, int teacherId) {
        super(firstName, lastName);
        this.courses = courses;
        this.teacherId = teacherId;
    }

    /**
     * Getter for Teacher.courses
     * @return List<Courses>
     */
    public List<Student> getCourses() {
        return courses;
    }

    /**
     * Setter for Teacher.courses
     * @param courses
     */
    public void setCourses(List<Student> courses) {
        this.courses = courses;
    }
}
