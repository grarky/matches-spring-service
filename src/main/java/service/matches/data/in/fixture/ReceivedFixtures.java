package service.matches.data.in.fixture;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedFixtures {

	@JsonProperty("league_name")
	private String leagueName;

	private List<ReceivedFixture> fixtures;

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public List<ReceivedFixture> getFixtures() {
		return fixtures;
	}

	public void setFixtures(List<ReceivedFixture> fixtures) {
		this.fixtures = fixtures;
	}

}
