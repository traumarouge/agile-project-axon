package agileproject.backlogitem.query;

import agileproject.backlogitem.command.domain.BacklogItemCreatedEvent;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class BacklogItemEventListenerTest {

    @Test
    void handleBacklogItemCreatedEvent() {

        BacklogItemRepository backlogItemRepository = mock(BacklogItemRepository.class);
        BacklogItemCreatedEvent event = new BacklogItemCreatedEvent("uuid", "name");

        BacklogItemEventListener sut = new BacklogItemEventListener(backlogItemRepository);
        sut.handle(event);

        ArgumentCaptor<BacklogItem> captor = ArgumentCaptor.forClass(BacklogItem.class);
        verify(backlogItemRepository).save(captor.capture());

        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setUuid("uuid");
        backlogItem.setName("name");
        assertThat(captor.getValue()).isEqualToComparingFieldByField(backlogItem);
    }
}
