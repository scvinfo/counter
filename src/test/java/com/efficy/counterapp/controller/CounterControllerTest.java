package com.efficy.counterapp.controller;

import com.efficy.counterapp.dto.Counter;
import com.efficy.counterapp.service.CounterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CounterController.class)
@WebAppConfiguration
@EnableWebMvc
public class CounterControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private CounterService counterService;

    private String baseUrl = "/v1/api/counter";

    @Test
    public void createCounter() throws Exception {
        Counter counter = new Counter(1, "counter1", 1);
        Mockito.when(counterService.createCounter(Mockito.any())).thenReturn(counter);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(getJsonString(counter))).andReturn();
        System.out.println(mvcResult.getResponse().getStatus());
        Assert.assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void getCountersTest() throws Exception {
        Mockito.when(counterService.getCounters()).thenReturn(getMockCounterList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/get")).andReturn();
        Assert.assertNotNull(mvcResult);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }


    @Test
    public void getCounterTest() throws Exception {
        final var counter = new Counter(1, "counter1", 1);
        Mockito.when(counterService.getCounter(Mockito.anyInt())).thenReturn(counter);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/get/1")).andReturn();
        Assert.assertNotNull(mvcResult);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void getCounterNotFoundTest() throws Exception {
        Mockito.when(counterService.getCounter(Mockito.anyInt())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/get/1")).andReturn();
        Assert.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void incrementCounterTest() throws Exception {
        final var counter = new Counter(1, "counter1", 1);
        Mockito.when(counterService.getCounter(Mockito.anyInt())).thenReturn(counter);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/increment/1")).andReturn();
        Assert.assertNotNull(mvcResult);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void incrementCounterInvalidIdTest() throws Exception {
        Mockito.when(counterService.getCounter(Mockito.anyInt())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/increment/1")).andReturn();
        Assert.assertEquals(404, mvcResult.getResponse().getStatus());
    }


    private String getJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private List<Counter> getMockCounterList() {
        List<Counter> countersList = new ArrayList<>();
        countersList.add(new Counter(1, "Counter1", 1));
        return countersList;
    }

}
