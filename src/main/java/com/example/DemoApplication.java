package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point of the application
 * The @EnableJpaAuditing enable the use of "Created_at" and "Updated_at"
 */
@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }
}
