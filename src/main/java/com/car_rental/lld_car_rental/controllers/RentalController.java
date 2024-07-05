package com.car_rental.lld_car_rental.controllers;

import com.car_rental.lld_car_rental.client.RestClient;
import com.car_rental.lld_car_rental.models.SortResponse;
import com.car_rental.lld_car_rental.models.Transaction;
import com.car_rental.lld_car_rental.models.TransactionResponse;
import com.car_rental.lld_car_rental.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rental/")
public class RentalController {

    @Autowired
    RestClient restClient;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/posts")
    public String getPosts() {
        return restClient.getPosts();
    }

    @GetMapping("/transactions/{id}")
    public TransactionResponse getTransactions(@PathVariable Integer id) {
        return transactionService.getTransactions(id);
    }

    @GetMapping("/getLast/{id}")
    public String getLast(@PathVariable Integer id) {
        return transactionService.getLast(id);
    }

    @GetMapping("/getSort/{id}")
    public SortResponse getSort(@PathVariable Integer id) {
        return transactionService.getSort(id);
    }
}



