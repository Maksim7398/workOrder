package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.exeption.NumberCarExeption;
import org.example.app.service.ClientService;
import org.example.web.model.Client;
import org.example.web.model.ClientIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("work-order")

public class ClientController {

    private final ClientService clientService;
    Logger logger = Logger.getLogger(ClientController.class);

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/client_table")
    public String client(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("clientIdToRemove", new ClientIdToRemove());
        model.addAttribute("clientList", clientService.retrieveAll());
        return "client_page";
    }

    @PostMapping("/save")
    public String saveClient(Client client,BindingResult bindingResult ,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("clientIdToRemove", new ClientIdToRemove());
            model.addAttribute("clientList", clientService.retrieveAll());
            logger.info("Вернул список результатов");
            return "client_page";

        }
        else {
            clientService.store(client);
            clientService.replace(client);
            logger.info(" save client! ");
            return "redirect:/work/page";
        }
    }
    @PostMapping("/remove")
    public String removeBook(@Valid ClientIdToRemove clientIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("client", new Client());
            model.addAttribute("clientList", clientService.retrieveAll());
            return "client_page";
        }else {
            clientService.removeItemById(clientIdToRemove.getId());
            return "redirect:/work-order/client_table";
        }
    }
    @PostMapping("/removeByRegex")
    public String removeByRegex(@RequestParam(value = "queryRegex",required = false) String queryRegex){
        clientService.removeItemByRegex(queryRegex);
        return "redirect:/work-order/client_table";

    }
    @GetMapping("/saveClientToNumber")
    public String autoGeneration( Client client,Model model) throws NumberCarExeption {
        client = clientService.autoGeneration(client.getNumberCar());
        logger.info(client.getNumberCar());

        try {
            model.addAttribute("numberCar",client.getNumberCar());
            logger.info(client);
            clientService.replace(client);
            return "redirect:/work/page";
        }catch (Exception ex){
            throw new NumberCarExeption("Номера нет в базе данных");
        }
    }
    @GetMapping("/search")
    @ModelAttribute("getClientByName")
    public List<Client> getClientsForName(Client client){
        return clientService.getClientToName(client.getFio());
    }


    @GetMapping(value = "/uploadFile", produces = { "application/octet-stream" })
    public ResponseEntity<byte[]> download() {

        try {

            File file = new File( "C:\\Users\\Acer\\IdeaProjects\\Work-order\\src\\main\\resources\\outZakaz.docx");


            byte[] contents = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename("work-order.docx").build());
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }










}
