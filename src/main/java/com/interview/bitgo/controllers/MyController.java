package com.interview.bitgo.controllers;

import com.interview.bitgo.client.RestClient;
import com.interview.bitgo.models.SortResponse;
import com.interview.bitgo.models.Transaction;
import com.interview.bitgo.models.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.interview.bitgo.service.TransactionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    RestClient restClient;

    @GetMapping("/posts")
    public String getPosts() {
        return restClient.getPosts();
    }

    @GetMapping("/transactions/{id}")
    public TransactionResponse getTransactions(@PathVariable Integer id) {
        String hash = restClient.getBlocHash(id);
        return restClient.getTransactions(hash);
    }

    @GetMapping("/getLast/{id}")
    public String getLast(@PathVariable Integer id) {
        String hash = restClient.getBlocHash(id);
        return restClient.getLastPage(hash);
    }

    @GetMapping("/getSort/{id}")
    public SortResponse getSort(@PathVariable Integer id) {
        TransactionResponse response = getTransactions(id);
        Map<String, List<String>> map = new HashMap<>();
        Map<String, Integer> answer = new HashMap<>();


        for(Transaction transaction : response.getTransactions()) {
            List<Transaction> vin = transaction.getVin();
            List<String> txns = new ArrayList<>();
            for(Transaction t : vin) {
                txns.add(t.getTxid());
            }
            map.put(transaction.getTxid(), txns);
        }

        for(Transaction transaction : response.getTransactions()) {
            String txid = transaction.getTxid();
            int count = 0;

            if(map.get(txid) == null) {
                answer.put(txid, 0);
                continue;
            }

            for(String link : map.get(txid)) {
                if(map.get(link) != null) {
                    count++;
                    helper(map, link, count);
                }
            }
            answer.put(txid, count);
        }

        SortResponse sortResponse = new SortResponse();
        sortResponse.setMap(answer);
        return sortResponse;
    }

    public void helper(Map<String, List<String>> map, String link, int count) {
        for(String txn : map.get(link)) {
            if(map.get(txn) != null) {
                count++;
                helper(map, txn, count);
            }
        }
    }
}