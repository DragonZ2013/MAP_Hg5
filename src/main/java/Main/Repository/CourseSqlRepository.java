package Main.Repository;

import Main.Model.Course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
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
    public Course create(Course obj) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mapsqlproject","root","1234");
        Statement statement = connection.createStatement();
        return null;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Course update(Course obj) {
        return null;
    }

    @Override
    public void delete(Course obj) {

    }

    @Override
    public Course getObject(int Id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
