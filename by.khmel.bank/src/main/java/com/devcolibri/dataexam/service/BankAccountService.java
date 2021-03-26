package com.devcolibri.dataexam.service;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;

import java.util.List;

public interface BankAccountService {
    BankAccount addBankAccount(BankAccount bankAccount);
    void delete(Long id);
    BankAccount getById(long id);
    void editBankAccount(BankAccount bankAccount,Long id);
    List<BankAccount> getAll();
}
