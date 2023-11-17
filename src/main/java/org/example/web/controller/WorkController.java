package org.example.web.controller;

import org.example.app.service.CompleteWorkService;
import org.example.web.model.CompleteWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("work")
@Scope("request")
public class WorkController {

    private final CompleteWorkService completeWorkService;
    @Autowired
    public WorkController(CompleteWorkService completeWorkService) {
        this.completeWorkService = completeWorkService;
    }


    @GetMapping(value ="/page")
    public String saveWorks(Model model){
        model.addAttribute("work",new CompleteWork());
        model.addAttribute("workList",completeWorkService.listWorks());
        completeWorkService.listWorks().clear();
      return "work_page";
    }

    @PostMapping("/save")
    public String saveBooks(CompleteWork work) {
        completeWorkService.getCompleteWorks(work);

        return "redirect:/work/page";
    }
    @GetMapping("/addFile")
    public String addFile(){
        completeWorkService.replace(completeWorkService.listWorks());
        completeWorkService.clear();
        return "redirect:/work-order/uploadFile";
    }

}
