package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.web.model.Admin;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    public boolean authenticate(Admin loginForm){
        logger.info("try auth with user form: " + loginForm);
        return loginForm.getUsername().equals("admin") && loginForm.getPassword().equals("123");
    }
}
