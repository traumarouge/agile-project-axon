package agileproject.backlogitem.query;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BacklogItemQueryHandlerTest {

    @Test
    void returnsBacklogItem() {

        BacklogItem backlogItem = new BacklogItem();
        BacklogItemRepository backlogItemRepository = mock(BacklogItemRepository.class);
        when(backlogItemRepository.findByUuid("uuid")).thenReturn(Optional.of(backlogItem));

        BacklogItemQueryHandler sut = new BacklogItemQueryHandler(backlogItemRepository);
        assertThat(sut.handle("uuid")).isEqualTo(backlogItem);
    }


    @Test
    void returnsNullBacklogItemNotFound() {

        BacklogItemRepository backlogItemRepository = mock(BacklogItemRepository.class);
        when(backlogItemRepository.findByUuid("uuid")).thenReturn(Optional.empty());

        BacklogItemQueryHandler sut = new BacklogItemQueryHandler(backlogItemRepository);
        assertThat(sut.handle("uuid")).isNull();
    }
}
