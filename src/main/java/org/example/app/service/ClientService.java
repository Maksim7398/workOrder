package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.app.exeption.NumberCarExeption;
import org.example.web.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class  ClientService {

    private  final ProjectRepository<Client> clientProjectRepository;
    Logger logger = Logger.getLogger(ClientService.class);

    @Autowired
    public ClientService(ProjectRepository<Client> clientProjectRepository) {
        this.clientProjectRepository = clientProjectRepository;
    }


    public Map<String, List<Client>> retrieveAll() {
        return clientProjectRepository.retrieveAll();
    }


    public void store(Client client) {
        clientProjectRepository.store( client);
    }


    public boolean removeItemById(Integer clientIdToRemove) {
       return clientProjectRepository.removeItemById(clientIdToRemove);
    }

    public File replace(Client client){
        logger.info(client);
        return clientProjectRepository.replace(client);
    }

    public  Client autoGeneration(String numberCar) throws NumberCarExeption {
        return clientProjectRepository.autoGeneration(numberCar);
    }


    public boolean removeItemByRegex(String queryRegex) {
        return clientProjectRepository.removeItemByRegex(queryRegex);
    }

    public List<Client> getClientToName(String name){
        return clientProjectRepository.searchClientToName(name);
    }


}
