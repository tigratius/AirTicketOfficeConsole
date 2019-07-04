package main.java.com.tigratius.airticketofficeconsole.repository;

import java.util.List;

public interface GenericSimpleRepository<T, ID> {

    T getById(ID id) throws Exception;

    List<T> getAll() throws Exception;

    List<T> stringToData(List<String> items) throws Exception;
}
