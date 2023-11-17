package org.example.web.model;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class WorksDto {
    @NotEmpty
    private List<CompleteWork> workList;

    public WorksDto() {
    }

    public WorksDto(List<CompleteWork> workList) {
        this.workList = workList;
    }

    public void addWorks(CompleteWork work){

        this.workList.add(work);
    }

    public List<CompleteWork> getWorkList() {
        return workList;
    }
}
