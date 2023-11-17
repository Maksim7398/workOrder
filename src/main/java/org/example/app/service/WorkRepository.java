package org.example.app.service;

import java.util.List;

public interface WorkRepository<T> {


    List<T> getWorksList();

    void store(T typesWorks);

    void clear();

    void replace(List <T> work);
}
