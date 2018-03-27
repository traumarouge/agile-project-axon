package agileproject.backlogitem.query;

import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseType;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;

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

        ResponseType<BacklogItem> responseType = ResponseTypes.instanceOf(BacklogItem.class);
        CompletableFuture<BacklogItem> future = queryGateway.query("BacklogItemByUuid", uuid, responseType);

        try {
            return Optional.ofNullable(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
