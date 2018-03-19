package agileproject.backlogitem.command.domain;

import agileproject.sprint.query.SprintQueryService;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.matchers.Matchers;

import org.hamcrest.Matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.axonframework.test.matchers.Matchers.sequenceOf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BacklogItemTest {

    private static final String UUID = java.util.UUID.randomUUID().toString();

    private FixtureConfiguration<BacklogItem> fixtureConfiguration;

    @BeforeEach
    void setup() {

        fixtureConfiguration = new AggregateTestFixture<>(BacklogItem.class);
    }


    @Test
    void createBacklogItem() {

        CreateBacklogItemCommand createBacklogItemCommand = new CreateBacklogItemCommand("name");

        Matcher<BacklogItemCreatedEvent> payloadMatcher = Matchers.predicate(e ->
                    e.getName().equals("name") && isUUID(e.getIdentifier()));

        fixtureConfiguration.given()
            .when(createBacklogItemCommand)
            .expectEventsMatching(sequenceOf(messageWithPayload(payloadMatcher)))
            .expectReturnValueMatching(Matchers.predicate(BacklogItemTest::isUUID));
    }


    @Test
    void createBacklogItemFailsNameIsNull() {

        CreateBacklogItemCommand createBacklogItemCommand = new CreateBacklogItemCommand(null);

        fixtureConfiguration.given()
            .when(createBacklogItemCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void createBacklogItemFailsNameIsEmpty() {

        CreateBacklogItemCommand createBacklogItemCommand = new CreateBacklogItemCommand("");

        fixtureConfiguration.given()
            .when(createBacklogItemCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void commitBacklogItem() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        CommitBacklogItemCommand commitCommand = new CommitBacklogItemCommand(UUID, "sprint-uuid");
        BacklogItemCommittedEvent committedEvent = new BacklogItemCommittedEvent(UUID, "sprint-uuid");

        SprintQueryService sprintQueryService = mock(SprintQueryService.class);
        when(sprintQueryService.doesSprintExist("sprint-uuid")).thenReturn(true);
        fixtureConfiguration.registerInjectableResource(sprintQueryService);

        fixtureConfiguration.given(createdEvent)
            .when(commitCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(committedEvent);
    }


    @Test
    void commitBacklogItemFailsNoSprint() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        CommitBacklogItemCommand commitCommand = new CommitBacklogItemCommand(UUID, "sprint-uuid");

        SprintQueryService sprintQueryService = mock(SprintQueryService.class);
        when(sprintQueryService.doesSprintExist("sprint-uuid")).thenReturn(false);
        fixtureConfiguration.registerInjectableResource(sprintQueryService);

        fixtureConfiguration.given(createdEvent)
            .when(commitCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void commitAlreadyCommittedBacklogItem() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        BacklogItemCommittedEvent committedFirstEvent = new BacklogItemCommittedEvent(UUID, "sprint-uuid");

        CommitBacklogItemCommand commitCommand = new CommitBacklogItemCommand(UUID, "sprint-uuid");
        BacklogItemUncommittedEvent uncommittedEvent = new BacklogItemUncommittedEvent(UUID, "sprint-uuid");
        BacklogItemCommittedEvent committedSecondEvent = new BacklogItemCommittedEvent(UUID, "sprint-uuid");

        SprintQueryService sprintQueryService = mock(SprintQueryService.class);
        when(sprintQueryService.doesSprintExist("sprint-uuid")).thenReturn(true);
        fixtureConfiguration.registerInjectableResource(sprintQueryService);

        fixtureConfiguration.given(createdEvent, committedFirstEvent)
            .when(commitCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(uncommittedEvent, committedSecondEvent);
    }


    @Test
    void renameBacklogItem() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        RenameBacklogItemCommand renameCommand = new RenameBacklogItemCommand(UUID, "other");
        BacklogItemRenamedEvent renamedEvent = new BacklogItemRenamedEvent(UUID, "other");

        fixtureConfiguration.given(createdEvent)
            .when(renameCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(renamedEvent);
    }


    @Test
    void renameBacklogItemFailsNameIsNull() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        RenameBacklogItemCommand renameCommand = new RenameBacklogItemCommand(UUID, null);

        fixtureConfiguration.given(createdEvent)
            .when(renameCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void renameBacklogItemFailsNameIsEmpty() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        RenameBacklogItemCommand renameCommand = new RenameBacklogItemCommand(UUID, "");

        fixtureConfiguration.given(createdEvent)
            .when(renameCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    private static boolean isUUID(String name) {

        try {
            java.util.UUID.fromString(name);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
