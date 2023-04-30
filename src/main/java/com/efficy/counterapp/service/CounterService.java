package com.efficy.counterapp.service;

import com.efficy.counterapp.dto.Counter;

import java.util.List;

/**
 * @author Y.Parasuram
 *
 */
public interface CounterService {
    List<Counter> getCounters();
    Counter getCounter(Integer id);
    Counter createCounter(Counter counter);
    Counter incrementCounter(Counter counter);
}
