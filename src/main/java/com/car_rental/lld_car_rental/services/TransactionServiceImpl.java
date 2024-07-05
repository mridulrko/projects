package com.car_rental.lld_car_rental.services;

import com.car_rental.lld_car_rental.client.RestClient;
import com.car_rental.lld_car_rental.models.SortResponse;
import com.car_rental.lld_car_rental.models.Transaction;
import com.car_rental.lld_car_rental.models.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    public RestClient restClient;

    @Override
    public TransactionResponse getTransactions(Integer transactionId) {
        String blockHash = restClient.getBlocHash(transactionId);
        TransactionResponse transactions = restClient.getTransactions(blockHash);
        return transactions;
    }

    @Override
    public String getPosts() {
        return restClient.getPosts();

    }

    @Override
    public String getLast(Integer id) {
        String hash = restClient.getBlocHash(id);
        System.out.println(hash);
        return restClient.getLastPage(hash);
    }

    @Override
    public SortResponse getSort(Integer id) {
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
                    count += helper(map, link);
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

//        sortedMap.forEach((key, value) -> System.out.println(key + ": " + value));

        return SortResponse.builder().map(sortedMap).build();
    }

    public Integer helper(Map<String, List<String>> map, String link) {
        Integer count = 0;
        for(String txn : map.get(link)) {
            if(map.get(txn) != null) {
                count++;
                count += helper(map, txn);
            }
        }
        System.out.printf(count + " ");
        return count;
    }
}
