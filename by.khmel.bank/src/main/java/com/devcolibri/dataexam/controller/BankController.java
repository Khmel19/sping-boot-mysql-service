package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.Bank;
import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.entity.Worker;
import com.devcolibri.dataexam.service.BankAccountService;
import com.devcolibri.dataexam.service.BankService;
import com.devcolibri.dataexam.service.ClientService;
import com.devcolibri.dataexam.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
public class BankController {

    private BankService bankService;
    private ClientService clientService;
    private BankAccountService bankAccountService;
    private WorkerService workerService;


    @Autowired
    public BankController(BankService bankService, ClientService clientService, BankAccountService bankAccountService, WorkerService workerService) {
        this.clientService = clientService;
        this.bankService = bankService;
        this.bankAccountService = bankAccountService;
        this.workerService = workerService;
    }

    @GetMapping("/")
    String allBanks(Model model) {
        model.addAttribute("banks", bankService.getAll());
        return "banks";
    }

    @GetMapping("/newBank")
    String newBank(Model model) {
        model.addAttribute("bank", new Bank());
        return "newBank";
    }

    @PostMapping
    String newBankSubmit(@ModelAttribute("bank") Bank bank) {
        bankService.addBank(bank);
        return "redirect:";
    }

    @GetMapping("/{id}")
    String showBank(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bank", bankService.getById(id));
        return "showBank";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        List<Client> clients = clientService.getAll();
        for (Client client : clients
        ) {
            if (id.equals(client.getBank().getId())) {
                Long clientId = client.getId();
                List<BankAccount> bankAccounts = bankAccountService.getAll();
                for (BankAccount account : bankAccounts
                ) {
                    if (clientId.equals(account.getClient().getId())) {
                        Long accountId = account.getId();
                        bankAccountService.delete(accountId);
                    }
                }
                clientService.delete(clientId);
            }
        }
        List<Worker> workers = workerService.getAll();
        for (Worker worker : workers
        ) {
            if (id.equals(worker.getBank().getId())) {
                Long workerId=worker.getId();
                workerService.delete(workerId);
            }
        }

        bankService.delete(id);
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String updateGet(@ModelAttribute("bank") Bank bank) {

        return "editBank";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("bank") Bank bank, Model model) {
        bankService.addBank(bank);
        return "redirect:/";
    }
}
