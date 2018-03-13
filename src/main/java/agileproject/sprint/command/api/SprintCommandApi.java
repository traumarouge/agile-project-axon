package agileproject.sprint.command.api;

import agileproject.sprint.command.domain.CreateSprintCommand;
import agileproject.sprint.command.domain.RenameSprintCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/sprints")
public class SprintCommandApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintCommandApi.class);

    private final CommandGateway commandGateway;

    @Autowired
    public SprintCommandApi(CommandGateway commandGateway) {

        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SprintDto sprint) {

        LOGGER.debug("Received POST request on /sprints");

        String identifier = commandGateway.sendAndWait(new CreateSprintCommand(sprint.name));

        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(identifier).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rename(@PathVariable("id") String identifier, @RequestBody SprintDto sprint) {

        LOGGER.debug("Received PUT request on /sprints/{}", identifier);

        commandGateway.sendAndWait(new RenameSprintCommand(identifier, sprint.name));
    }
}
