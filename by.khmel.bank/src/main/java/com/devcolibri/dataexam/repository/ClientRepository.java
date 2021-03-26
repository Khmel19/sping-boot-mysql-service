package com.devcolibri.dataexam.repository;

import com.devcolibri.dataexam.entity.Bank;
import com.devcolibri.dataexam.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select b from Client b where b.id = :id")
    Client findById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("update Client c set c.firstName = ?1, c.lastName = ?2,c.address=?3,c.email=?4,c.phoneNumber=?5   where c.id = ?6")
    void setUserInfoById(String firstname, String lastname,String address,String email, String phone, Long userId);
}
