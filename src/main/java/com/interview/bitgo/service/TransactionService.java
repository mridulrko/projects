package com.interview.bitgo.service;

import com.interview.bitgo.models.SortResponse;
import com.interview.bitgo.models.TransactionResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface TransactionService {

    TransactionResponse getTransactions(Integer transactionId);

    String getPosts();

    String getLast(Integer id);

    SortResponse getSort(Integer id);
}
