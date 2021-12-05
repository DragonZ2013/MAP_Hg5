package Main.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {

    T getObject(int Id) throws SQLException;

    T create(T obj) throws SQLException;

    List<T> getAll() throws SQLException;

    T update(T obj);

    void delete(int id);

    void close() throws IOException;
}
