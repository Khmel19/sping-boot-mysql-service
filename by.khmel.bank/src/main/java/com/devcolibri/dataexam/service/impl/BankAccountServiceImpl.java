package com.devcolibri.dataexam.service.impl;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.repository.BankAccountRepository;
import com.devcolibri.dataexam.repository.BankRepository;
import com.devcolibri.dataexam.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Override
    public BankAccount addBankAccount(BankAccount bankAccount) {
        BankAccount savedAccount=bankAccountRepository.saveAndFlush(bankAccount);
        return savedAccount;
    }

    @Override
    public void delete(Long id) {
        bankAccountRepository.deleteById(id);
    }

    @Override
    public BankAccount getById(long id) {

        return bankAccountRepository.findById(id);
    }

    @Override
    public void editBankAccount(BankAccount bankAccount, Long id) {
        bankAccountRepository.setUserInfoById(bankAccount.getCurrency(),bankAccount.getAmount(),bankAccount.getAmountOfCredit(),id);
    }

    @Override
    public List<BankAccount> getAll() {
        return bankAccountRepository.findAll();
    }
}
