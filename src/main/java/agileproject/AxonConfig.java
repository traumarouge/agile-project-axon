package agileproject;

import org.axonframework.commandhandling.SimpleCommandBus;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;

import org.axonframework.config.EventProcessingConfiguration;

import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;

import org.axonframework.messaging.interceptors.BeanValidationInterceptor;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.axonframework.serialization.upcasting.event.EventUpcasterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import static java.util.Collections.*;


@Configuration
public class AxonConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AxonConfig.class);

    @Autowired
    public void registerBeanValidationInterceptor(SimpleCommandBus simpleCommandBus) {

        LOGGER.debug("Register dispatch interceptor {}", BeanValidationInterceptor.class);
        simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
    }


    @Autowired
    public void registerTrackingProcessors(EventProcessingConfiguration eventProcessingConfiguration) {

        String processorName = "SprintEventTrackingProcessor";

        LOGGER.debug("Register tracking event processor {}", processorName);
        eventProcessingConfiguration.registerTrackingEventProcessor(processorName);
    }


    @Bean
    public <E extends EventUpcaster> EventUpcasterChain eventUpcasterChain(Optional<List<E>> upcasters) {

        return upcasters.map(EventUpcasterChain::new).orElseGet(() -> new EventUpcasterChain(emptyList()));
    }


    @Bean
    public JpaEventStorageEngine eventStorageEngine(Serializer serializer, EventUpcasterChain eventUpcasterChain,
        DataSource dataSource, EntityManagerProvider entityManagerProvider, TransactionManager transactionManager)
        throws SQLException {

        return new JpaEventStorageEngine(serializer, eventUpcasterChain, dataSource, entityManagerProvider,
                transactionManager);
    }
}
