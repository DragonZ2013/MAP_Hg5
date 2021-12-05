package Main.Repository;

import Main.Model.Course;
import Main.Model.Student;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentFileRepository extends InMemoryRepository<Student> implements FileRepository<Student>{


    private String filename;
    /**
     * Constructor for StudentRepository Objects + File initializer
     * @param filename
     * @throws IOException
     */
    public StudentFileRepository(CourseFileRepository courseFileRepository, String filename) throws IOException {
        super();

        this.filename=filename;
        BufferedReader fixReader = new BufferedReader(new FileReader(filename));

        String line = fixReader.readLine().replace("\\","");

        fixReader.close();

        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.replace(0,1,"[");
        stringBuilder.replace(line.length()-2,line.length(),"]");

        BufferedWriter fixWriter = new BufferedWriter(new FileWriter(filename));

        fixWriter.write(stringBuilder.toString());
        fixWriter.close();
        Reader studentReader = new BufferedReader(new FileReader(filename));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(studentReader);

        for (JsonNode n: parser ){
            List<Course> tempCourses = new ArrayList<>();
            String courses = n.path("enrolledCourses").asText();
            String[] splitCourses = courses.replace("[","").replace("]","").replace(" ","").split(",");
            List<java.lang.Integer> coursesId = new ArrayList<>(Arrays.asList(splitCourses)).stream().map(java.lang.Integer::valueOf).toList();
            for (Course c: courseFileRepository.getAll())
                for(java.lang.Integer cId: coursesId)
                    if(cId==c.getCourseId())
                        tempCourses.add(c);
            Student s = new Student(n.path("firstName").asText(),n.path("lastName").asText(),n.path("studentId").asInt(),n.path("totalCredits").asInt(),tempCourses);
            this.create(s);
            for(Course c:tempCourses)
            {
                c.getStudentsEnrolled().add(s);
                courseFileRepository.update(c);
            }

        }
        this.close();
    }

    /**
     * Updates a Student object from the Memory Repository array given the ID of the parameter object
     * @param obj
     * @return Student -modified object
     */
    @Override
    public Student update(Student obj) {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentId() == obj.getStudentId())
                .findFirst()
                .orElseThrow();
        studentToUpdate.setEnrolledCourses(obj.getEnrolledCourses());
        studentToUpdate.setTotalCredits(obj.getTotalCredits());
        studentToUpdate.setFirstName(obj.getFirstName());
        studentToUpdate.setLastName(obj.getLastName());


        return studentToUpdate;

    }

    /**
     * Saves the StudentRepository to given path
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        String serializedStudent = "";

        for (Student s : repoList){
            objectMapper.registerModule(new SimpleModule().addSerializer(Student.class, new StudentSerializer()));

            serializedStudent += objectMapper.writeValueAsString(s);

            serializedStudent += ",";

            writer.writeValue(new File(this.filename),serializedStudent);

        }

    }
}
