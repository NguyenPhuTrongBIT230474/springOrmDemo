package com.example.demospringorm;

import com.example.demospringorm.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemospringormApplication implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(DemospringormApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
            customerService.testAllQueries();
    }
}