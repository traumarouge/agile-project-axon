package agileproject.sprint.query;

import agileproject.sprint.command.domain.SprintCreatedEvent;
import agileproject.sprint.command.domain.SprintRenamedEvent;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


class SprintEventListenerTest {

    @Test
    void onSprintCreatedEvent() {

        SprintRepository sprintRepository = mock(SprintRepository.class);

        SprintEventListener sut = new SprintEventListener(sprintRepository);
        sut.on(new SprintCreatedEvent("uuid", "name"));

        ArgumentCaptor<Sprint> captor = ArgumentCaptor.forClass(Sprint.class);
        verify(sprintRepository).save(captor.capture());

        Sprint sprint = new Sprint();
        sprint.setUuid("uuid");
        sprint.setName("name");

        assertThat(captor.getValue()).isEqualToComparingFieldByField(sprint);
    }


    @Test
    void onSprintRenamedEvent() {

        Sprint sprint = mock(Sprint.class);
        SprintRepository sprintRepository = mock(SprintRepository.class);
        when(sprintRepository.findByUuid("uuid")).thenReturn(Optional.of(sprint));

        SprintEventListener sut = new SprintEventListener(sprintRepository);
        sut.on(new SprintRenamedEvent("uuid", "new name"));

        verify(sprint).setName("new name");
        verifyNoMoreInteractions(sprint);
    }
}
