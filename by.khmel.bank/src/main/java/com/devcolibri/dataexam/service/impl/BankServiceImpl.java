package com.devcolibri.dataexam.service.impl;

import com.devcolibri.dataexam.entity.Bank;
import com.devcolibri.dataexam.repository.BankRepository;
import com.devcolibri.dataexam.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;


    @Override
    public Bank addBank(Bank bank) {
        Bank savedBank = bankRepository.saveAndFlush(bank);

        return savedBank;
    }

    @Override
    public void delete(long id) {
        bankRepository.deleteById(id);
    }

    @Override
    public Bank getByName(String name) {
        return bankRepository.findByName(name);
    }

    @Override
    public Bank getById(long id) {
        return bankRepository.findById(id);
    }

    @Override
    public Bank editBank(Bank bank) {
        return bankRepository.saveAndFlush(bank);
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }


}
