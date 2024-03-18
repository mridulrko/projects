package com.interview.bitgo.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null properties in JSON
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortResponse {
    private Map<String, Integer> map;
}