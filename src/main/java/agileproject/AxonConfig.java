package agileproject;

import org.axonframework.commandhandling.SimpleCommandBus;

import org.axonframework.config.EventProcessingConfiguration;

import org.axonframework.messaging.interceptors.BeanValidationInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;


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
}
