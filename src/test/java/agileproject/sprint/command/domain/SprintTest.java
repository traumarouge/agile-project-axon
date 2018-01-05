package agileproject.sprint.command.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SprintTest {

    private FixtureConfiguration fixtureConfiguration;

    @BeforeEach
    void setup() {

        fixtureConfiguration = new AggregateTestFixture<>(Sprint.class);
    }


    @Test
    void createSprint() {

        CreateSprintCommand createSprintCommand = new CreateSprintCommand("identifier", "sprint name");
        SprintCreatedEvent sprintCreatedEvent = new SprintCreatedEvent("identifier", "sprint name");

        fixtureConfiguration.given()
            .when(createSprintCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(sprintCreatedEvent);
    }
}
