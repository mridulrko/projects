package com.car_rental.lld_car_rental.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null properties in JSON
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String txid;
    private List<Transaction> vin;
}