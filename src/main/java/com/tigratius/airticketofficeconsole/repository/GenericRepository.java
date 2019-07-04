package main.java.com.tigratius.airticketofficeconsole.repository;

import java.util.List;

public interface GenericRepository<T, ID> extends GenericSimpleRepository<T, ID> {

    void delete(T item) throws Exception;

    void update(T item) throws Exception;

    void save(T item);

    ID getLastId() throws Exception;

    List<String> dataToString(List<T> items);

    String dataToString(T item);
}
