package com.car_rental.lld_car_rental.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null properties in JSON
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortResponse {
    private Map<String, Integer> map;
}