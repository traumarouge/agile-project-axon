package agileproject;

import org.axonframework.commandhandling.SimpleCommandBus;

import org.axonframework.messaging.interceptors.BeanValidationInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AgileProjectApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgileProjectApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(AgileProjectApplication.class, args);
    }


    @Autowired
    public void registerBeanValidationInterceptor(SimpleCommandBus simpleCommandBus) {

        LOGGER.debug("Register dispatch interceptor {}", BeanValidationInterceptor.class);
        simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
    }
}
