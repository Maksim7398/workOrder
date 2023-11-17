package org.example.app.service;

import org.example.app.exeption.NumberCarExeption;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ProjectRepository<T> {

        Map<String, List<T>> retrieveAll();

        void store(T client);

        boolean removeItemById(Integer clientIdToRemove);


        boolean removeItemByRegex(String queryRegex);

        File replace(T client);

        T autoGeneration(String numberCar) throws NumberCarExeption;

        List<T> searchClientToName(String name);


}
