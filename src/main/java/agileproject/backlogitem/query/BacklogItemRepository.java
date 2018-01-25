package agileproject.backlogitem.query;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface BacklogItemRepository extends CrudRepository<BacklogItem, Long> {

    Optional<BacklogItem> findByUuid(String uuid);
}
