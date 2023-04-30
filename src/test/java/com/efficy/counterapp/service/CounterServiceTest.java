package com.efficy.counterapp.service;

import com.efficy.counterapp.dto.Counter;
import com.efficy.counterapp.exception.InValidCounterException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CounterService.class)
@WebAppConfiguration
public class CounterServiceTest {

    @Spy
    CounterServiceImpl counterService;

    @Before
    public void setUp() {
        Map<Integer, Counter> mockedMap = new ConcurrentHashMap<>();
        mockedMap.put(1, new Counter(1, "Counter-1", 1111));
        mockedMap.put(2, new Counter(2, "Counter-2", 2222));
        mockedMap.put(3, new Counter(3, "Counter-3", 3333));
        //ReflectionTestUtils.set
        ReflectionTestUtils.setField(counterService, "countersMap", mockedMap);
    }

    @Test
    public void getCountersTest() {
        Assert.assertEquals(3, counterService.getCounters().size());
    }

    @Test
    public void getCounterTest() {
        Assert.assertEquals("Counter-1", counterService.getCounter(1).getName());
    }

    @Test(expected = InValidCounterException.class)
    public void createCounterWithInValidTest() {
        counterService.createCounter(new Counter(0, "Counter-4", 4444));
    }

    @Test
    public void createCounterTest() {
        Assert.assertEquals(Integer.valueOf(4), counterService.createCounter(new Counter(4, "Counter-4", 4444)).getId());
    }

    @Test
    public void incrementCounterTest() {
        Counter counter = new Counter(1, "Counter-1", 1111);
        counter = counterService.incrementCounter(counter);
        Assert.assertEquals(Integer.valueOf(1112), counter.getValue());
    }
}
