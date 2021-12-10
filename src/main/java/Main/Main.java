package Main;

import Main.Controller.Controller;

import Main.Repository.*;
import Main.UI.ConsoleView;


public class Main {


    public static void main(String[] args) {

        String connURL="jdbc:mysql://localhost:3306/mapsqlproject";
        String connUser="root";
        String connPassword="1234";
        TeacherSqlRepository tr = new TeacherSqlRepository(connURL,connUser,connPassword);
        CourseSqlRepository cr = new CourseSqlRepository(connURL,connUser,connPassword);
        StudentSqlRepository sr = new StudentSqlRepository(connURL,connUser,connPassword);

        Controller cont = new Controller(cr,tr,sr);
        ConsoleView cw = new ConsoleView(cont);
        cw.Run();


    }
}
