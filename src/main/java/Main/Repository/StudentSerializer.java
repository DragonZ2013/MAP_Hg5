package Main.Repository;

import Main.Model.Course;
import Main.Model.Student;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentSerializer extends JsonSerializer<Student> {

    /**
     * Converts student object to json format
     * @param student
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("studentId", student.getStudentId());
        jsonGenerator.writeStringField("firstName", student.getFirstName());
        jsonGenerator.writeStringField("lastName", student.getLastName());
        jsonGenerator.writeNumberField("totalCredits", student.getTotalCredits());
        List<java.lang.Integer> idList = new ArrayList<>();
        for (Course c : student.getEnrolledCourses())
            idList.add(c.getCourseId());
        jsonGenerator.writeStringField("enrolledCourses",String.valueOf(idList));
        jsonGenerator.writeEndObject();



    }
}
