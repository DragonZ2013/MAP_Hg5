package Main;

import Main.Controller.Controller;
import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;
import Main.UI.ConsoleView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * !!! REPOSITORIES MUST BE LOADED IN ORDER: TEACHER -> COURSE -> STUDENT
     */
    public static void main(String[] args) throws IOException, ExistentIdException, MissingIdException, MaxSizeException {

        TeacherRepository tr = new TeacherRepository("TeacherData.json");
        CourseRepository cr = new CourseRepository(tr,"CourseData.json");
        StudentRepository sr = new StudentRepository(cr,"StudentData.json");
        Controller cont = new Controller(cr,tr,sr);
        ConsoleView cw = new ConsoleView(cont);
        cw.Run();

    }
}
