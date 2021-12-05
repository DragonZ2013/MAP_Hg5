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
    public Student create(Student obj) {
        return null;
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public Student update(Student obj) {
        return null;
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
