package service.matches.data.in.event;

import java.util.Map;

import service.matches.data.in.event.odds.ReceivedOddsValueWrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedEvent {

	@JsonProperty("hn")
	private String homeTeam;

	@JsonProperty("an")
	private String awayTeam;

	@JsonProperty("t")
	private long time;

	@JsonProperty("o")
	private Map<String, ReceivedOddsValueWrapper> odds;

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

	public Map<String, ReceivedOddsValueWrapper> getOdds() {
		return odds;
	}

	public void setOdds(Map<String, ReceivedOddsValueWrapper> odds) {
		this.odds = odds;
	}

}
