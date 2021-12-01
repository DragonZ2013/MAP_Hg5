package Main.Repository;

import java.util.List;

public interface CrudRepository<T> {

    T create(T obj);

    List<T> getAll();

    T update(T obj);

    void delete(T obj);
}
