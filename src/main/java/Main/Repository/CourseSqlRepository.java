package Main.Repository;

import Main.Model.Course;
import Main.Model.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseSqlRepository implements CrudRepository<Course>{

    private String connUrl;
    private String connUser;
    private String connPassword;

    public CourseSqlRepository(String connUrl, String connUser, String connPassword) {
        this.connUrl = connUrl;
        this.connUser = connUser;
        this.connPassword = connPassword;
    }

    @Override
    public void create(Course obj) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "insert into courses(id,coursename,teacherid,maxenrollment,credits) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,obj.getCourseId());
        preparedStatement.setString(2,obj.getName());
        preparedStatement.setInt(3,obj.getTeacher());
        preparedStatement.setInt(4,obj.getMaxEnrollment());
        preparedStatement.setInt(5,obj.getCredits());
        preparedStatement.execute();
    }

    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courseList= new ArrayList<Course>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from courses");
        while(resultSet.next()){
            Course tempCourse = new Course(resultSet.getString("coursename"),resultSet.getInt("teacherid"),resultSet.getInt("maxenrollment"),new ArrayList<Integer>(),resultSet.getInt("credits"),resultSet.getInt("id"));
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where courseid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                tempCourse.getStudentsEnrolled().add(enrolledStudents.getInt("studentid"));
            }
            courseList.add(tempCourse);
        }

        connection.close();
        return courseList;
    }

    @Override
    public void update(Course obj) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "update courses set coursename=?,teacherid=?,maxenrollment=?,credits=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(5,obj.getCourseId());
        preparedStatement.setString(1,obj.getName());
        preparedStatement.setInt(2,obj.getTeacher());
        preparedStatement.setInt(3,obj.getMaxEnrollment());
        preparedStatement.setInt(4,obj.getCredits());

        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "delete from courses where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.execute();

    }

    @Override
    public Course getObject(int id) throws SQLException {
        Course retCourse = null;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from courses where id= "+ id);
        while(resultSet.next()){
            retCourse = new Course(resultSet.getString("coursename"),resultSet.getInt("teacherid"),resultSet.getInt("maxenrollment"),new ArrayList<Integer>(),resultSet.getInt("credits"),resultSet.getInt("id"));
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where courseid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                retCourse.getStudentsEnrolled().add(enrolledStudents.getInt("studentid"));
            }
        }

        connection.close();
        return retCourse;
    }

    @Override
    public void close() throws IOException {

    }
}
