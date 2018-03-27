package agileproject.sprint.query;

import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseType;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class SprintQueryServiceTest {

    @Test
    void sprintByUuid() {

        Sprint sprint = new Sprint();
        String query = "sprint-uuid";

        QueryGateway queryGateway = mock(QueryGateway.class);
        CompletableFuture<Sprint> future = CompletableFuture.completedFuture(sprint);
        when(queryGateway.query(eq("SprintByUuid"), eq(query), ArgumentMatchers.<ResponseType<Sprint>>any()))
            .thenReturn(future);

        SprintQueryService sut = new SprintQueryService(queryGateway);
        Optional<Sprint> optional = sut.sprintByUuid("sprint-uuid");
        assertThat(optional).isPresent().contains(sprint);
    }


    @Test
    void sprintNotFound() {

        String query = "sprint-uuid";

        QueryGateway queryGateway = mock(QueryGateway.class);
        CompletableFuture<Sprint> future = CompletableFuture.completedFuture(null);
        when(queryGateway.query(eq("SprintByUuid"), eq(query), ArgumentMatchers.<ResponseType<Sprint>>any()))
            .thenReturn(future);

        SprintQueryService sut = new SprintQueryService(queryGateway);
        Optional<Sprint> optional = sut.sprintByUuid("sprint-uuid");
        assertThat(optional).isEmpty();
    }
}
