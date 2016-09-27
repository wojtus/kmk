package spectrum.kmk.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TeamBo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private Long value;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getValue() {
		return value;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setValue(final Long value) {
		this.value = value;
	}

}
