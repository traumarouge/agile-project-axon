package agileproject;

import org.axonframework.commandhandling.SimpleCommandBus;

import org.axonframework.config.EventHandlingConfiguration;

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
    public void registerTrackingProcessors(EventHandlingConfiguration eventHandlingConfiguration) {

        final String processorName = "SprintEventTrackingProcessor";
        eventHandlingConfiguration.registerTrackingProcessor(processorName)
            .registerHandlerInterceptor(processorName, conf -> new DoubleTrackedEventInterceptor());
    }
}
