package com.interview.bitgo.service;

import com.interview.bitgo.client.RestClient;
import com.interview.bitgo.models.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;

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
}
