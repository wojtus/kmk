package spectrum.kmk.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FixtureBo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	private TeamBo home;
	@ManyToOne
	private TeamBo away;
	private int goalsHome;
	private int goalsAway;

	public TeamBo getAway() {
		return away;
	}

	public int getGoalsAway() {
		return goalsAway;
	}

	public int getGoalsHome() {
		return goalsHome;
	}

	public TeamBo getHome() {
		return home;
	}

	public long getId() {
		return id;
	}

	public void setAway(final TeamBo away) {
		this.away = away;
	}

	public void setGoalsAway(final int goalsAway) {
		this.goalsAway = goalsAway;
	}

	public void setGoalsHome(final int goalsHome) {
		this.goalsHome = goalsHome;
	}

	public void setHome(final TeamBo home) {
		this.home = home;
	}

}
