package Main.Repository;

import Main.Model.Course;
import Main.Model.Teacher;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts teacher object to json format
 */
public class TeacherSerializer extends JsonSerializer<Teacher> {
    @Override
    public void serialize(Teacher teacher, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("teacherId",teacher.getTeacherId());
        jsonGenerator.writeStringField("firstName",teacher.getFirstName());
        jsonGenerator.writeStringField("lastName",teacher.getLastName());

        jsonGenerator.writeEndObject();

    }
}
