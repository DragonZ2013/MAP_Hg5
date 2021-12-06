package Main.Repository;

import Main.Model.Course;
import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherSqlRepository implements CrudRepository<Teacher>{

    private String connUrl;
    private String connUser;
    private String connPassword;

    public TeacherSqlRepository(String connUrl, String connUser, String connPassword) {
        this.connUrl = connUrl;
        this.connUser = connUser;
        this.connPassword = connPassword;
    }

    @Override
    public void create(Teacher obj) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "insert into teachers(id,firstname,lastname) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,obj.getTeacherId());
        preparedStatement.setString(2,obj.getFirstName());
        preparedStatement.setString(3,obj.getLastName());
        preparedStatement.execute();
    }

    @Override
    public List<Teacher> getAll() throws SQLException {
        List<Teacher> teacherList= new ArrayList<Teacher>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teachers");
        while(resultSet.next()){
            Teacher tempTeacher = new Teacher(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"));
            teacherList.add(tempTeacher);
        }

        connection.close();
        return teacherList;
    }

    @Override
    public void update(Teacher obj) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "update teachers set firstname=?,lastname=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(3,obj.getTeacherId());
        preparedStatement.setString(1,obj.getFirstName());
        preparedStatement.setString(2,obj.getLastName());

        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        String query = "delete from teachers where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.execute();

    }

    @Override
    public Teacher getObject(int id) throws SQLException {
        Teacher retTeacher = null;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teachers where id= "+ id);
        while(resultSet.next()){
            retTeacher = new Teacher(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"));
        }

        connection.close();
        return retTeacher;
    }

    @Override
    public void close() throws IOException {

    }
}
