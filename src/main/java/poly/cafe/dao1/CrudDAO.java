package poly.cafe.dao1;

import java.util.List;

public interface CrudDAO<T, ID> {
    T create(T entity);
    void update(T entity);
    void deleteById(ID id);
    List<T> findAll();
    T findById(ID id);
}
