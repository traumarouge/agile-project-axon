package agileproject.backlogitem.query;

import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class BacklogItemQueryService {

    private final QueryGateway queryGateway;

    @Autowired
    public BacklogItemQueryService(QueryGateway queryGateway) {

        this.queryGateway = queryGateway;
    }

    public Optional<BacklogItem> backlogItemByUuid(String uuid) {

        CompletableFuture<BacklogItem> future = queryGateway.send(uuid, "BacklogItemByUuid", BacklogItem.class);

        try {
            return Optional.ofNullable(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
