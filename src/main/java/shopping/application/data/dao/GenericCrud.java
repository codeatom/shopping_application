package shopping.application.data.dao;

import java.util.List;
import java.util.Optional;

public interface GenericCrud<T, ID>{
    T save(T t);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean delete (ID id);
}
