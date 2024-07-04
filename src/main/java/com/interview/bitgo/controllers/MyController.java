package com.interview.bitgo.controllers;

import com.interview.bitgo.client.RestClient;
import com.interview.bitgo.models.SortResponse;
import com.interview.bitgo.models.Transaction;
import com.interview.bitgo.models.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
            for(Transaction t : vin) txns.add(t.getTxid());
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
                    count += helper(map, link, answer);
                }
            }
            answer.put(txid, count);
            System.out.println("txid: " + txid + " count: " + count);
        }

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        answer.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));

        SortResponse sortResponse = new SortResponse();
        sortResponse.setMap(sortedMap);
        return sortResponse;
    }

    public Integer helper(Map<String, List<String>> map, String link, Map<String, Integer> answer) {
        Integer count = 0;
        if(answer.get(link) != null) return answer.get(link);

        for(String txn : map.get(link)) {
            if(map.get(txn) != null) {
                count++;
                count += helper(map, txn, answer);
            }
        }
        answer.put(link, count);
        System.out.printf(count + " ");
        return count;
    }

    public
}