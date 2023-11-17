package org.example.web.controller;

import org.example.app.exeption.NumberCarExeption;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErorController {

    @GetMapping("/404")
    public String notFoundError(){
        return "errors/404";
    }
    @ExceptionHandler(NumberCarExeption.class)
    public String handleError(Model model, NumberCarExeption exception){
        model.addAttribute("errorMessage",exception.getMessage());
        return "errors/404";
    }
}
