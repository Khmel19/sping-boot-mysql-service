package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.service.BankAccountService;
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
public class BankAccountController {

    private Long clientId;
    private BankAccountService bankAccountService;
    private ClientService clientService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, ClientService clientService) {
        this.bankAccountService = bankAccountService;
        this.clientService = clientService;
    }

    @GetMapping("/{id}/clients/{idC}/accounts")
    String allAccounts(Model model, @PathVariable("idC") Long idC, @PathVariable("id") Long id) {
        List<BankAccount> accounts = bankAccountService.getAll();
        List<BankAccount> accountList = new ArrayList<>();
        for (BankAccount account : accounts
        ) {
            if (idC.equals(account.getClient().getId())) {
                accountList.add(account);
            }
        }
        model.addAttribute("accountsList", accountList);
        model.addAttribute("id", id);
        model.addAttribute("idC", idC);
        return "accounts";
    }

    @GetMapping("/{id}/clients/{idC}/accounts/newAccount")
    String newAccount(Model model, @PathVariable String id, @PathVariable("idC") Long idC) {
        clientId = idC;
        System.out.println(clientId);

        BankAccount bankAccount = new BankAccount();
        model.addAttribute("account", bankAccount);
        return "newAccount";
    }

    @PostMapping("/newAccount")
    String submitNewAccount(@ModelAttribute("account") BankAccount bankAccount) {
        bankAccount.setClient(clientService.getById(clientId));
        bankAccountService.addBankAccount(bankAccount);
        return "redirect:";
    }

    @GetMapping("/{id}/clients/{idC}/accounts/{idAc}")
    String showAccount(Model model, @PathVariable("idAc") Long idAc,@PathVariable("id") Long id, @PathVariable("idC") Long idC) {
        model.addAttribute("account", bankAccountService.getById(idAc));
        System.out.println(bankAccountService.getById(idAc).getAmount());
        return "showAccount";
    }
    @DeleteMapping("/{id}/clients/{idC}/accounts/{idAc}")
    String deleteAccount(@PathVariable Long idAc){
        bankAccountService.delete(idAc);
        return "redirect:";
    }

    @GetMapping("/{id}/clients/{idC}/accounts/{idAc}/editAccount")
    String updateAccountGet(@ModelAttribute("account") BankAccount bankAccount, @PathVariable String idC, @PathVariable String idAc, @PathVariable String id){
        clientId=Long.parseLong(idC);
        return "editAccount";
    }
    @PatchMapping("/{id}/clients/{idC}/accounts/{idAc}/editAccount")
    String updateAccount(@ModelAttribute("aacount") BankAccount bankAccount, @PathVariable Long idAc){

        bankAccount.setClient(clientService.getById(clientId));
        bankAccountService.editBankAccount(bankAccount,idAc);
        return "redirect:";
    }
}
