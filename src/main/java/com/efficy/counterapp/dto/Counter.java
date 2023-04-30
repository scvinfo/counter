package com.efficy.counterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Y.Parasuram
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Counter {
    private Integer id;
    private String name;
    private Integer value;
    }
