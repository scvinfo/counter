package com.efficy.counterapp.service;

import com.efficy.counterapp.dto.Counter;
import com.efficy.counterapp.exception.InValidCounterException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Y.Parasuram
 */
@Service
public class CounterServiceImpl implements CounterService {

    private static Map<Integer, Counter> countersMap = new ConcurrentHashMap<>();

    /**
     * Get List of Counters
     */
    @Override
    public List<Counter> getCounters() {
        return countersMap.values().stream().collect(Collectors.toList());
    }

    /**
     * Returns Counter
     *
     * @param id
     * @return
     */
    @Override
    public Counter getCounter(final Integer id) {
        return countersMap.get(id);
    }

    /**
     * Creates the counter and adds to Map
     *
     * @param counter
     * @return
     */
    @Override
    public Counter createCounter(Counter counter) {
        if (counter.getId() <= 0 ) {
            throw new InValidCounterException("CounterId Should be Grater than 0");
        }
        addCounterToMap(counter);
        return counter;
    }

    /**
     * Increment counter value by one
     *
     * @param counter
     */
    @Override
    public Counter incrementCounter(Counter counter) {
        synchronized (counter) {
            counter.setValue(counter.getValue() + 1);
        }
       return counter;
    }

    /**
     * Adds counter to counter map
     *
     * @param counter
     */
    private void addCounterToMap(final Counter counter) {
        final var counterId = counter.getId();
        if (countersMap.containsKey(counterId)) {
            throw new InValidCounterException("Counter already exists");
        }
        countersMap.put(counterId, counter);
    }
}
