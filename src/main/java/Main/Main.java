package Main;

import Main.Controller.Controller;
import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Repository.*;
import Main.UI.ConsoleView;

import java.io.IOException;
import java.sql.*;

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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students");
        while(resultSet.next()){
            System.out.println(resultSet.getString("id")+" "+resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
        }

        connection.close();


    }
}
