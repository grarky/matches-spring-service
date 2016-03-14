package service.matches.data.in.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedFixture {

	@JsonProperty("home_team")
	private String homeTeam;

	@JsonProperty("away_team")
	private String awayTeam;

	@JsonProperty("start")
	private long time;

	@JsonProperty("event_slug")
	private String eventLink;

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getEventLink() {
		return eventLink;
	}

	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}

}
