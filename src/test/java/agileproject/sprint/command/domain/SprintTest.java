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


    private static boolean isUUID(String name) {

        try {
            java.util.UUID.fromString(name);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
