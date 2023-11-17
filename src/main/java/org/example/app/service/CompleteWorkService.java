package org.example.app.service;

import org.example.web.model.CompleteWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompleteWorkService  {

    private final WorkRepository<CompleteWork> completeWorkWorkRepository;
    @Autowired
    public CompleteWorkService(WorkRepository<CompleteWork> completeWorkWorkRepository) {
        this.completeWorkWorkRepository = completeWorkWorkRepository;
    }

    public List<CompleteWork> listWorks(){
        return completeWorkWorkRepository.getWorksList();
    }


    public void getCompleteWorks (CompleteWork work) {
         completeWorkWorkRepository.store(work);
    }

    public void clear(){
        completeWorkWorkRepository.clear();
    }

    public void replace(List<CompleteWork> works){
        completeWorkWorkRepository.replace(works);
    }
}
