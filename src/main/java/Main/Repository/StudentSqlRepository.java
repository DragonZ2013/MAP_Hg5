package Main.Repository;

import Main.Model.Student;
import Main.Model.Teacher;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentSqlRepository implements CrudRepository<Student>{

    private String connUrl;
    private String connUser;
    private String connPassword;

    public StudentSqlRepository(String connUrl, String connUser, String connPassword) {
        this.connUrl = connUrl;
        this.connUser = connUser;
        this.connPassword = connPassword;
    }

    @Override
    public void create(Student obj) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "insert into students(id,firstname,lastname,totalcredits) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,obj.getStudentId());
        preparedStatement.setString(2,obj.getFirstName());
        preparedStatement.setString(3,obj.getLastName());
        preparedStatement.setInt(4,obj.getTotalCredits());
        preparedStatement.execute();

    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> studentList= new ArrayList<Student>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students");
        while(resultSet.next()){
            Student tempStudent = new Student(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"),resultSet.getInt("totalcredits"),new ArrayList<Integer>());
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where studentid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                tempStudent.getEnrolledCourses().add(enrolledStudents.getInt("courseid"));
            }
            studentList.add(tempStudent);
        }

        connection.close();
        return studentList;
    }

    @Override
    public void update(Student obj) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "update students set firstname=?,lastname=?,totalcredits=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(3,obj.getStudentId());
        preparedStatement.setString(1,obj.getFirstName());
        preparedStatement.setString(2,obj.getLastName());
        preparedStatement.setInt(3,obj.getTotalCredits());

        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "delete from students where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.execute();

    }

    @Override
    public Student getObject(int id) throws SQLException {
        Student retStudent= null;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students where id= "+ id);
        while(resultSet.next()){
            retStudent = new Student(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"),resultSet.getInt("totalcredits"),new ArrayList<Integer>());
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where studentid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                retStudent.getEnrolledCourses().add(enrolledStudents.getInt("courseid"));
            }
        }

        connection.close();
        return retStudent;
    }

    @Override
    public void close() throws IOException {

    }
}
