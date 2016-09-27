package spectrum.kmk.persistence;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<TeamBo, Long> {

	TeamBo findByName(String name);

}
