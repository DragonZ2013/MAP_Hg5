package Main.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {

    T getObject(int Id);

    T create(T obj) throws SQLException;

    List<T> getAll();

    T update(T obj);

    void delete(T obj);

    void close() throws IOException;
}
