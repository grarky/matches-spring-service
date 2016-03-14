package service.matches.data.out.matchDetails;

import java.util.List;

public class MatchDetails {

	private String homeTeam;
	private String awayTeam;
	private String time;

	private List<Odds> bestOdds;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Odds> getBestOdds() {
		return bestOdds;
	}

	public void setBestOdds(List<Odds> bestOdds) {
		this.bestOdds = bestOdds;
	}

}
