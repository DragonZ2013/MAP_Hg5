package Main.Repository;

import Main.Model.Student;

import java.io.*;
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
    public void create(Student obj) {

    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public void update(Student obj) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Student getObject(int Id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
