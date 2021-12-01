package Main.Repository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements CrudRepository<T> {


    protected List<T> repoList;

    /**
     * Constructor for InMemoryRepository objects
     */
    public InMemoryRepository() {
        this.repoList = new ArrayList<>();

    }

    /**
     * adds new elements to the object List
     * @param obj
     * @return T
     */
    @Override
    public T create(T obj) {
        this.repoList.add(obj);
        return obj;
    }

    /**
     * returns the List of objects
     * @return List<T>
     *
     */
    @Override
    public List<T> getAll() {
        return this.repoList;
    }

    /**
     * deletes an element from the List
     * @param obj
     */
    @Override
    public void delete(T obj) {
        this.repoList.remove(obj);
    }

}
