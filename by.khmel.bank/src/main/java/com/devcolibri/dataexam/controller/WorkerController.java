package com.devcolibri.dataexam.controller;

import com.devcolibri.dataexam.entity.BankAccount;
import com.devcolibri.dataexam.entity.Client;
import com.devcolibri.dataexam.entity.Worker;
import com.devcolibri.dataexam.service.BankService;
import com.devcolibri.dataexam.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc
public class WorkerController {

    private Long bankId;
    private WorkerService workerService;
    private BankService bankService;

    @Autowired
    public WorkerController(WorkerService workerService, BankService bankService) {
        this.workerService = workerService;
        this.bankService = bankService;
    }

    @GetMapping("/{id}/workers")
    String allWorkers(Model model, @PathVariable Long id) {
        List<Worker> workers = workerService.getAll();
        List<Worker> workerList = new ArrayList<>();
        for (Worker worker : workers
        ) {
            if (id.equals(worker.getBank().getId())) {
                workerList.add(worker);
            }
        }
        model.addAttribute("workers", workerList);
        model.addAttribute("idd", id);
        return "workers";
    }

    @GetMapping("/{id}/workers/newWorker")
    String newWorker(Model model, @PathVariable String id) {
        bankId = Long.parseLong(id);
        System.out.println(bankId);

        Worker worker = new Worker();
        model.addAttribute("worker", worker);
        return "newWorker";
    }

    @PostMapping("/newWorker")
    String submitNewWorker(@ModelAttribute("worker") Worker worker) {
        worker.setBank(bankService.getById(bankId));
        workerService.addWorker(worker);
        return "redirect:";
    }

    @GetMapping("/{id}/workers/{idW}")
    String showWorker(@PathVariable("idW") Long id, Model model) {
        model.addAttribute("worker", workerService.getById(id));
        return "showWorker";
    }

    @DeleteMapping("/{id}/workers/{idW}")
    String deleteWorker(@PathVariable("idW") Long id) {
        workerService.delete(id);
        return "redirect:";
    }

    @GetMapping("/{id}/workers/{idW}/editWorker")
    String updateWorkerGet(@ModelAttribute("worker") Worker worker, @PathVariable String idW, @PathVariable String id) {
        bankId = Long.parseLong(id);
        return "editWorker";
    }

    @PatchMapping("/{id}/workers/{idW}/editWorker")
    String updateClient(@ModelAttribute("worker") Worker worker, @PathVariable Long idW) {

        worker.setBank(bankService.getById(bankId));
        workerService.editWorker(worker, idW);

        return "redirect:/";
    }
}
