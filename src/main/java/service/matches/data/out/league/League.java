package service.matches.data.out.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {

	private String code;
	private String name;

	private String scoresLink;
	private String fixturesLink;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScoresLink() {
		return scoresLink;
	}

	public void setScoresLink(String scoresLink) {
		this.scoresLink = scoresLink;
	}

	public String getFixturesLink() {
		return fixturesLink;
	}

	public void setFixturesLink(String fixturesLink) {
		this.fixturesLink = fixturesLink;
	}

}
