package agileproject.sprint.query;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class SprintQueryHandlerTest {

    @Test
    void returnsSprint() {

        Sprint sprint = new Sprint();
        SprintRepository sprintRepository = mock(SprintRepository.class);
        when(sprintRepository.findByUuid("uuid")).thenReturn(Optional.of(sprint));

        SprintQueryHandler sut = new SprintQueryHandler(sprintRepository);
        assertThat(sut.handle("uuid")).isEqualTo(sprint);
    }


    @Test
    void returnsNullSprintNotFound() {

        SprintRepository sprintRepository = mock(SprintRepository.class);
        when(sprintRepository.findByUuid("uuid")).thenReturn(Optional.empty());

        SprintQueryHandler sut = new SprintQueryHandler(sprintRepository);
        assertThat(sut.handle("uuid")).isNull();
    }
}
