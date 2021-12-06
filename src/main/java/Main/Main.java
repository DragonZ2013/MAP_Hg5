package Main;

import Main.Controller.Controller;
import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.*;
import Main.Repository.*;
import Main.UI.ConsoleView;

import java.io.IOException;
import java.lang.annotation.Target;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    /**
     * !!! REPOSITORIES MUST BE LOADED IN ORDER: TEACHER -> COURSE -> STUDENT
     */
    public static void main(String[] args) throws IOException, ExistentIdException, MissingIdException, MaxSizeException, SQLException {

        /*
        TeacherFileRepository tr = new TeacherFileRepository("TeacherData.json");
        CourseFileRepository cr = new CourseFileRepository(tr,"CourseData.json");
        StudentFileRepository sr = new StudentFileRepository(cr,"StudentData.json");
        Controller cont = new Controller(cr,tr,sr);
        ConsoleView cw = new ConsoleView(cont);
        cw.Run();*
         */

        TeacherSqlRepository tr = new TeacherSqlRepository("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        CourseSqlRepository cr = new CourseSqlRepository("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        StudentSqlRepository sr = new StudentSqlRepository("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");


        for(Course c:cr.getAll())
            System.out.println(c);
        for(Student s:sr.getAll())
            System.out.println(s);
        for(Teacher t:tr.getAll())
            System.out.println(t);

        System.out.println();
        System.out.println(cr.getObject(4));
        System.out.println(cr.getObject(1));
        System.out.println(cr.getObject(2));
        System.out.println(cr.getObject(5));
        System.out.println(cr.getObject(6));
        cr.delete(3);
        System.out.println(cr.getObject(3));
        Course c = new Course("Sisteme de operare",3,20,new ArrayList<>(),35,3);
        cr.create(c);
        System.out.println(cr.getObject(3));
        c.setMaxEnrollment(30);
        cr.update(c);
        System.out.println(cr.getObject(3));

    }
}
