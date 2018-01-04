package agileproject;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AgileProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(AgileProjectApplication.class, args);
    }


    @Bean
    public EventStorageEngine eventStorageEngine() {

        return new InMemoryEventStorageEngine();
    }
}
