package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.service.BankAccountService;
import com.devcolibri.dataexam.service.BankService;
import com.devcolibri.dataexam.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc
public class ClientController {

    private Long bankId;
    private ClientService clientService;
    private BankService bankService;
    private BankAccountService bankAccountService;

    @Autowired
    public ClientController(ClientService clientService, BankService bankService, BankAccountService bankAccountService) {
        this.clientService = clientService;
        this.bankService = bankService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/{id}/clients")
    String allClients(Model model, @PathVariable Long id) {
        List<Client> clients = clientService.getAll();
        List<Client> clientList = new ArrayList<>();
        for (Client client : clients
        ) {
            if (id.equals(client.getBank().getId())) {
                clientList.add(client);
            }
        }
        model.addAttribute("clients", clientList);
        model.addAttribute("idd", id);
        return "clients";
    }

    @GetMapping("/{id}/clients/newClient")
    String newClient(Model model, @PathVariable String id) {
        bankId = Long.parseLong(id);
        System.out.println(bankId);

        Client client = new Client();
        model.addAttribute("client", client);
        return "newClient";
    }

    @PostMapping("/newClient")
    String submitNewClient(@ModelAttribute("client") Client client) {
        client.setBank(bankService.getById(bankId));
        clientService.addClient(client);
        return "redirect:";
    }

    @GetMapping("/{id}/clients/{idC}")
    String showClient(@PathVariable("idC") Long id, Model model) {
        model.addAttribute("client", clientService.getById(id));
        return "showClient";
    }

    @DeleteMapping("/{id}/clients/{idC}")
    String deleteClient(@PathVariable("idC") Long id) {
        List<BankAccount> bankAccounts = bankAccountService.getAll();
        for (BankAccount account : bankAccounts
        ) {
            if (id.equals(account.getClient().getId())) {
                Long accountId=account.getId();
                bankAccountService.delete(accountId);
            }
        }
        clientService.delete(id);
        return "redirect:";
    }


    @GetMapping("/{id}/clients/{idC}/editClient")
    String updateClientGet(@ModelAttribute("client") Client client, @PathVariable String idC, @PathVariable String id) {
        bankId = Long.parseLong(id);
        return "editClient";
    }

    @PatchMapping("/{id}/clients/{idC}/editClient")
    String updateClient(@ModelAttribute("client") Client client, @PathVariable Long idC) {

        client.setBank(bankService.getById(bankId));
        clientService.editClient(client, idC);

        return "redirect:/";
    }
}
