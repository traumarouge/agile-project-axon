package agileproject.backlogitem.query;

import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseType;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BacklogItemQueryServiceTest {

    @Test
    void backlogItemByUuid() {

        BacklogItem backlogItem = new BacklogItem();
        String query = "backlogItem-uuid";

        QueryGateway queryGateway = mock(QueryGateway.class);
        CompletableFuture<BacklogItem> future = CompletableFuture.completedFuture(backlogItem);
        when(queryGateway.query(eq("BacklogItemByUuid"), eq(query), ArgumentMatchers.<ResponseType<BacklogItem>>any()))
            .thenReturn(future);

        BacklogItemQueryService sut = new BacklogItemQueryService(queryGateway);
        Optional<BacklogItem> optional = sut.backlogItemByUuid("backlogItem-uuid");
        assertThat(optional).isPresent().contains(backlogItem);
    }


    @Test
    void backlogItemNotFound() {

        String query = "backlogItem-uuid";

        QueryGateway queryGateway = mock(QueryGateway.class);
        CompletableFuture<BacklogItem> future = CompletableFuture.completedFuture(null);
        when(queryGateway.query(eq("BacklogItemByUuid"), eq(query), ArgumentMatchers.<ResponseType<BacklogItem>>any()))
            .thenReturn(future);

        BacklogItemQueryService sut = new BacklogItemQueryService(queryGateway);
        Optional<BacklogItem> optional = sut.backlogItemByUuid("backlogItem-uuid");
        assertThat(optional).isEmpty();
    }
}
