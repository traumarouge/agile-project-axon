package agileproject.backlogitem.query.api;

import agileproject.backlogitem.query.BacklogItem;
import agileproject.backlogitem.query.BacklogItemQueryService;

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
@RequestMapping("/backlogitems")
public class BacklogItemQueryApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogItemQueryApi.class);

    private final BacklogItemQueryService backlogItemQueryService;

    @Autowired
    public BacklogItemQueryApi(BacklogItemQueryService backlogItemQueryService) {

        this.backlogItemQueryService = backlogItemQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BacklogItem> get(@PathVariable("id") String uuid) {

        LOGGER.debug("Received GET request on /backlogitems/{}", uuid);

        return backlogItemQueryService.backlogItemByUuid(uuid)
            .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
