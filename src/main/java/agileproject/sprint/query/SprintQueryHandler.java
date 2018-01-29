package agileproject.sprint.query;

import org.axonframework.queryhandling.QueryHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


@Component
public class SprintQueryHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintQueryHandler.class);

    private final SprintRepository sprintRepository;

    @Autowired
    public SprintQueryHandler(SprintRepository sprintRepository) {

        this.sprintRepository = sprintRepository;
    }

    @QueryHandler(queryName = "SprintByUuid")
    public Sprint handle(String uuid) {

        LOGGER.debug("Handling query SprintByUuid: {}", uuid);

        return sprintRepository.findByUuid(uuid).orElse(null);
    }
}
