package org.example.web.model;

import javax.validation.constraints.NotNull;

public class ClientIdToRemove {
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
