package agileproject.backlogitem.query;

import org.axonframework.queryhandling.QueryHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


@Component
public class BacklogItemQueryHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogItemQueryHandler.class);

    private final BacklogItemRepository backlogItemRepository;

    @Autowired
    public BacklogItemQueryHandler(BacklogItemRepository backlogItemRepository) {

        this.backlogItemRepository = backlogItemRepository;
    }

    @QueryHandler(queryName = "BacklogItemByUuid")
    public BacklogItem handle(String uuid) {

        LOGGER.debug("Handling query BacklogItemByUuid: {}", uuid);

        return backlogItemRepository.findByUuid(uuid).orElse(null);
    }
}
