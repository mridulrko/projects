package com.interview.bitgo.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interview.bitgo.models.Transaction;
import com.interview.bitgo.models.TransactionResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestClient {

    private final RestTemplate restTemplate;

    @Autowired
    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    ObjectMapper mapper;

    public String getPosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        return restTemplate.getForObject(url, String.class);
    }

    public String getBlocHash(Integer id) {
        String url = "https://blockstream.info/api/block-height/" + id;
        return restTemplate.getForObject(url, String.class);
    }

    public TransactionResponse getTransactions(String hash) {

        try {
            List<Transaction> transactions = new ArrayList<>();

            int i=0;
            while (true) {
                try {
                    String url = "https://blockstream.info/api/block/" + hash + "/txs/" + i;
                    String jsonResponse = restTemplate.getForObject(url, String.class);
                    List<Transaction> transactionList = mapper.readValue(jsonResponse, new TypeReference<List<Transaction>>() {
                    });
                    Thread.sleep(1000);
                    System.out.println("Done for i -> "+ i);
                    transactions.addAll(transactionList);
                    i += 25;
                }
                catch (HttpClientErrorException e) {
                    System.out.println("reached end on i -> " + i);
                    break;
                }
            }
            TransactionResponse response = new TransactionResponse();
            response.setTransactions(transactions);
            return response;
    }
       catch (Exception e) {
           System.out.println(e.getMessage());
           return null;
       }
    }

    public String getLastPage(String hash) {

        try {
            String url = "https://blockstream.info/api/block/" + hash + "/txs/10000";
            String jsonResponse = restTemplate.getForObject(url, String.class);
            List<Transaction> transactionList = mapper.readValue(jsonResponse, new TypeReference<List<Transaction>>() {
            });
            System.out.println("mridul");
            System.out.println(transactionList);
            return transactionList.toString();
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}