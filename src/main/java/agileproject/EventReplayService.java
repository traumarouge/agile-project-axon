package agileproject;

import org.axonframework.config.EventProcessingConfiguration;

import org.axonframework.eventhandling.tokenstore.TokenStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EventReplayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventReplayService.class);

    private final EventProcessingConfiguration eventProcessingConfiguration;
    private final TokenStore tokenStore;

    @Autowired
    public EventReplayService(EventProcessingConfiguration eventProcessingConfiguration, TokenStore tokenStore) {

        this.eventProcessingConfiguration = eventProcessingConfiguration;
        this.tokenStore = tokenStore;
    }

    public void replay(String processorName) {

        LOGGER.info("Replaying events on processor '{}'", processorName);

        eventProcessingConfiguration.eventProcessor(processorName).ifPresentOrElse(processor -> {
                processor.shutDown();
                tokenStore.fetchToken(processorName, 0); // claims token
                tokenStore.storeToken(null, processorName, 0);
                processor.start();
            },
            () -> { throw new IllegalArgumentException(); });
    }
}
