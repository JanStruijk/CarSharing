package carsharing.DAO;

import java.util.List;

public abstract class DAO<T> {
    public abstract void insert(T obj);
    public abstract List<T> getAll();
}