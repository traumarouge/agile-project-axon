package agileproject.backlogitem.command.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.matchers.Matchers;

import org.hamcrest.Matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.axonframework.test.matchers.Matchers.sequenceOf;


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

        fixtureConfiguration.given(createdEvent)
            .when(commitCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(committedEvent);
    }


    @Test
    void commitAlreadyCommittedBacklogItem() {

        BacklogItemCreatedEvent createdEvent = new BacklogItemCreatedEvent(UUID, "name");
        BacklogItemCommittedEvent committedFirstEvent = new BacklogItemCommittedEvent(UUID, "sprint-uuid");

        CommitBacklogItemCommand commitCommand = new CommitBacklogItemCommand(UUID, "sprint-uuid");
        BacklogItemUncommittedEvent uncommittedEvent = new BacklogItemUncommittedEvent(UUID, "sprint-uuid");
        BacklogItemCommittedEvent committedSecondEvent = new BacklogItemCommittedEvent(UUID, "sprint-uuid");

        fixtureConfiguration.given(createdEvent, committedFirstEvent)
            .when(commitCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(uncommittedEvent, committedSecondEvent);
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
