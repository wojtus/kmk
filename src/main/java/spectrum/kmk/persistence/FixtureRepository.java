package spectrum.kmk.persistence;

import org.springframework.data.repository.CrudRepository;

public interface FixtureRepository extends CrudRepository<FixtureBo, Long> {

	FixtureBo findByHomeAndAway(TeamBo home, TeamBo away);

}
