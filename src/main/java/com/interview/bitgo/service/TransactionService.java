package com.interview.bitgo.service;

import com.interview.bitgo.models.SortResponse;
import com.interview.bitgo.models.TransactionResponse;

public interface TransactionService {

    TransactionResponse getTransactions(Integer transactionId);

    String getPosts();

    String getLast(Integer id);

    SortResponse getSort(Integer id);
}
