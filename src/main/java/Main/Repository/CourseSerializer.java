package Main.Repository;

import Main.Model.Course;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Converts course object to json format
 */
public class CourseSerializer extends JsonSerializer<Course> {
    @Override
    public void serialize(Course course, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("courseId",course.getCourseId());
        jsonGenerator.writeNumberField("teacher",course.getTeacher().getTeacherId());
        jsonGenerator.writeStringField("name",course.getName());
        jsonGenerator.writeNumberField("maxEnrollment",course.getMaxEnrollment());
        jsonGenerator.writeNumberField("credits",course.getCredits());


        jsonGenerator.writeEndObject();
    }
}
