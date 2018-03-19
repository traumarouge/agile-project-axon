package agileproject.backlogitem.query;

import agileproject.backlogitem.command.domain.BacklogItemCreatedEvent;
import agileproject.backlogitem.command.domain.BacklogItemRenamedEvent;

import org.axonframework.eventhandling.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class BacklogItemEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogItemEventListener.class);

    private final BacklogItemRepository backlogItemRepository;

    @Autowired
    public BacklogItemEventListener(BacklogItemRepository backlogItemRepository) {

        this.backlogItemRepository = backlogItemRepository;
    }

    @EventHandler
    public void on(BacklogItemCreatedEvent event) {

        LOGGER.debug("Handling BacklogItemCreatedEvent: {}", event.getIdentifier());

        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setUuid(event.getIdentifier());
        backlogItem.setName(event.getName());

        backlogItemRepository.save(backlogItem);
    }


    @EventHandler
    public void on(BacklogItemRenamedEvent event) {

        LOGGER.debug("Handling BacklogItemRenamedEvent: {}", event.getIdentifier());

        backlogItemRepository.findByUuid(event.getIdentifier())
            .orElseThrow(IllegalStateException::new)
            .setName(event.getName());
    }
}
