package com.efficy.counterapp.controller;

import java.util.List;

import com.efficy.counterapp.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efficy.counterapp.dto.Counter;

/**
 * @author Y.Parasuram
 *
 */
@RestController
@RequestMapping("v1/api/counter")
public class CounterController {

    private static final Logger logger = LoggerFactory.getLogger(CounterController.class);

    @Autowired
    private CounterService counterService;

   @PostMapping
   public ResponseEntity<Counter> createCounter(@RequestBody Counter counter) {
        logger.debug("Counter creation request : {}", counter);
        final var created = counterService.createCounter(counter);
        logger.debug("Created Counter {}", created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

 
   /*
    * Updated to switch for jdk 17 feature implementation.
    */
	@GetMapping("/get")
    public ResponseEntity<List<Counter>> getCounters() {
        final var counters = counterService.getCounters();
        logger.debug("Counters found : {}", counters);
        return switch (counters.size()){
        case 0 -> new ResponseEntity<>(counters, HttpStatus.NO_CONTENT);
        default -> new ResponseEntity<>(counters, HttpStatus.OK);
        };
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Counter> getCounter(@PathVariable Integer id) {
        logger.debug("Counter get with id {}");
        final var counter = counterService.getCounter(id);
        if (counter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Counter found : {}", counter);
        return new ResponseEntity<>(counter, HttpStatus.OK);
    }

    @PutMapping("/increment/{id}")
    public ResponseEntity<Counter> incrementCounter(@PathVariable Integer id) {
        var counter = counterService.getCounter(id);
        if (counter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        counter = counterService.incrementCounter(counter);
        return new ResponseEntity<>(counter, HttpStatus.OK);
    }

}
