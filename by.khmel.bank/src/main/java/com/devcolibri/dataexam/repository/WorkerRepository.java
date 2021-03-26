package com.devcolibri.dataexam.repository;

import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("select b from Worker b where b.id = :id")
    Worker findById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("update Worker c set c.firstName = ?1, c.lastName = ?2,c.phoneNumber=?3,c.status=?4   where c.id = ?5")
    void setWorkerInfoById(String firstname, String lastname,String phoneNumber,Integer status, Long workerId);


}
