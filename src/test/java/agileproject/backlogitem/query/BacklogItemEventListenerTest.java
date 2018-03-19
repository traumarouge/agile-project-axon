package agileproject.backlogitem.query;

import agileproject.backlogitem.command.domain.BacklogItemCreatedEvent;
import agileproject.backlogitem.command.domain.BacklogItemRenamedEvent;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


class BacklogItemEventListenerTest {

    @Test
    void onBacklogItemCreatedEvent() {

        BacklogItemRepository backlogItemRepository = mock(BacklogItemRepository.class);
        BacklogItemCreatedEvent event = new BacklogItemCreatedEvent("uuid", "name");

        BacklogItemEventListener sut = new BacklogItemEventListener(backlogItemRepository);
        sut.on(event);

        ArgumentCaptor<BacklogItem> captor = ArgumentCaptor.forClass(BacklogItem.class);
        verify(backlogItemRepository).save(captor.capture());

        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setUuid("uuid");
        backlogItem.setName("name");
        assertThat(captor.getValue()).isEqualToComparingFieldByField(backlogItem);
    }


    @Test
    void onBacklogItemRenamedEvent() {

        BacklogItem backlogItem = mock(BacklogItem.class);
        BacklogItemRepository backlogItemRepository = mock(BacklogItemRepository.class);
        when(backlogItemRepository.findByUuid("uuid")).thenReturn(Optional.of(backlogItem));

        BacklogItemEventListener sut = new BacklogItemEventListener(backlogItemRepository);
        sut.on(new BacklogItemRenamedEvent("uuid", "new name"));

        verify(backlogItem).setName("new name");
        verifyNoMoreInteractions(backlogItem);
    }
}
