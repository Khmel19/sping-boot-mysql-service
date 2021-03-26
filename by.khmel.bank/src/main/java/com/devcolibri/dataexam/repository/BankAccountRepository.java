package com.devcolibri.dataexam.repository;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
    @Query("select b from BankAccount b where b.id = :id")
    BankAccount findById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("update BankAccount c set c.currency = ?1, c.amount = ?2,c.amountOfCredit=?3   where c.id = ?4")
    void setUserInfoById(Double currency, Double amount,Double amountOfCredit, Long accountId);
}
