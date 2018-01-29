package agileproject.sprint.query.api;

import agileproject.sprint.query.Sprint;
import agileproject.sprint.query.SprintQueryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sprints")
public class SprintQueryApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintQueryApi.class);

    private SprintQueryService sprintQueryService;

    @Autowired
    public SprintQueryApi(SprintQueryService sprintQueryService) {

        this.sprintQueryService = sprintQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sprint> get(@PathVariable("id") String uuid) {

        LOGGER.debug("Received GET request on /sprints/{}", uuid);

        return sprintQueryService.sprintByUuid(uuid)
            .map(sprint -> new ResponseEntity<>(sprint, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
