package service.matches.data.out.score;

public class Score {

	private String homeTeam;
	private int homeTeamGoals;
	
	private String awayTeam;
	private int awayTeamGoals;

	private int matchDay;

	private String date;

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

	public int getHomeTeamGoals() {
		return homeTeamGoals;
	}

	public void setHomeTeamGoals(int homeTeamGoals) {
		this.homeTeamGoals = homeTeamGoals;
	}

	public int getAwayTeamGoals() {
		return awayTeamGoals;
	}

	public void setAwayTeamGoals(int awayTeamGoals) {
		this.awayTeamGoals = awayTeamGoals;
	}

	public int getMatchDay() {
		return matchDay;
	}

	public void setMatchDay(int matchDay) {
		this.matchDay = matchDay;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
