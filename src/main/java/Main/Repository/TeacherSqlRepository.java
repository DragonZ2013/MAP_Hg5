package Main.Repository;

import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
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
    public Teacher create(Teacher obj) {
        return null;
    }

    @Override
    public List<Teacher> getAll() {
        return null;
    }

    @Override
    public Teacher update(Teacher obj) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Teacher getObject(int Id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
