package agileproject.sprint.query;

import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class SprintQueryService {

    private final QueryGateway queryGateway;

    @Autowired
    public SprintQueryService(QueryGateway queryGateway) {

        this.queryGateway = queryGateway;
    }

    public Optional<Sprint> sprintByUuid(String uuid) {

        CompletableFuture<Sprint> future = queryGateway.send(uuid, "SprintByUuid", Sprint.class);

        try {
            return Optional.ofNullable(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean doesSprintExist(String uuid) {

        return sprintByUuid(uuid).isPresent();
    }
}