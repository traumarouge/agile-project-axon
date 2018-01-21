package agileproject.sprint.query;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface SprintRepository extends CrudRepository<Sprint, Long> {

    Optional<Sprint> findByUuid(String uuid);
}
