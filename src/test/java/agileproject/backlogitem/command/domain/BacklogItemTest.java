package agileproject.backlogitem.command.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BacklogItemTest {

    private FixtureConfiguration fixtureConfiguration;

    @BeforeEach
    void setup() {

        fixtureConfiguration = new AggregateTestFixture<>(BacklogItem.class);
    }


    @Test
    void createBacklogItem() {

        CreateBacklogItemCommand createBacklogItemCommand = new CreateBacklogItemCommand("identifier", "name");
        BacklogItemCreatedEvent backlogItemCreatedEvent = new BacklogItemCreatedEvent("identifier", "name");

        fixtureConfiguration.given()
            .when(createBacklogItemCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(backlogItemCreatedEvent);
    }
}
