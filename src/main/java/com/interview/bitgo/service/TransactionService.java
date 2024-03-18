package com.interview.bitgo.service;

import com.interview.bitgo.models.TransactionResponse;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    TransactionResponse getTransactions(Integer transactionId);

    String getPosts();
}
