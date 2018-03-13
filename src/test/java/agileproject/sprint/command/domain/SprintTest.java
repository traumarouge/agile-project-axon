package agileproject.sprint.command.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.matchers.Matchers;

import org.hamcrest.Matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.axonframework.test.matchers.Matchers.sequenceOf;


class SprintTest {

    private static final String UUID = java.util.UUID.randomUUID().toString();

    private FixtureConfiguration<Sprint> fixtureConfiguration;

    @BeforeEach
    void setup() {

        fixtureConfiguration = new AggregateTestFixture<>(Sprint.class);
    }


    @Test
    void createSprint() {

        CreateSprintCommand createSprintCommand = new CreateSprintCommand("name");

        Matcher<SprintCreatedEvent> payloadMatcher = Matchers.predicate(e ->
                    e.getName().equals("name") && isUUID(e.getIdentifier()));

        fixtureConfiguration.given()
            .when(createSprintCommand)
            .expectEventsMatching(sequenceOf(messageWithPayload(payloadMatcher)))
            .expectReturnValueMatching(Matchers.predicate(SprintTest::isUUID));
    }


    @Test
    void createSprintFailsNameIsNull() {

        CreateSprintCommand createSprintCommand = new CreateSprintCommand(null);

        fixtureConfiguration.given()
            .when(createSprintCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void createSprintFailsNameIsEmpty() {

        CreateSprintCommand createSprintCommand = new CreateSprintCommand("");

        fixtureConfiguration.given()
            .when(createSprintCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void renameSprint() {

        SprintCreatedEvent createdEvent = new SprintCreatedEvent(UUID, "name");
        RenameSprintCommand renameCommand = new RenameSprintCommand(UUID, "other");
        SprintRenamedEvent renamedEvent = new SprintRenamedEvent(UUID, "other");

        fixtureConfiguration.given(createdEvent)
            .when(renameCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(renamedEvent);
    }


    @Test
    void renameSprintFailsNameIsNull() {

        SprintCreatedEvent createdEvent = new SprintCreatedEvent(UUID, "name");
        RenameSprintCommand renameCommand = new RenameSprintCommand(UUID, null);

        fixtureConfiguration.given(createdEvent)
            .when(renameCommand)
            .expectException(IllegalArgumentException.class)
            .expectNoEvents();
    }


    @Test
    void renameSprintFailsNameIsEmpty() {

        SprintCreatedEvent createdEvent = new SprintCreatedEvent(UUID, "name");
        RenameSprintCommand renameCommand = new RenameSprintCommand(UUID, "");

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
