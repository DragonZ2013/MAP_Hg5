package Main.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileRepository<T> {

    void close() throws IOException;
}
