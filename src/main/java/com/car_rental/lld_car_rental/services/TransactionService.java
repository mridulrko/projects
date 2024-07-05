package com.car_rental.lld_car_rental.services;


import com.car_rental.lld_car_rental.models.SortResponse;
import com.car_rental.lld_car_rental.models.TransactionResponse;

public interface TransactionService {

    TransactionResponse getTransactions(Integer transactionId);

    String getPosts();

    String getLast(Integer id);

    SortResponse getSort(Integer id);
}
