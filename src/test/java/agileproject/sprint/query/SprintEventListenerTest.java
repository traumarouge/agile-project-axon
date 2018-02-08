package agileproject.sprint.query;

import agileproject.sprint.command.domain.SprintCreatedEvent;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class SprintEventListenerTest {

    @Test
    void handleSprintCreatedEvent() {

        SprintRepository sprintRepository = mock(SprintRepository.class);
        SprintCreatedEvent event = new SprintCreatedEvent("uuid", "name");

        SprintEventListener sut = new SprintEventListener(sprintRepository);
        sut.handle(event);

        ArgumentCaptor<Sprint> captor = ArgumentCaptor.forClass(Sprint.class);
        verify(sprintRepository).save(captor.capture());

        Sprint sprint = new Sprint();
        sprint.setUuid("uuid");
        sprint.setName("name");
        assertThat(captor.getValue()).isEqualToComparingFieldByField(sprint);
    }
}
