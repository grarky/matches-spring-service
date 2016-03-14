package service.matches.data.out.matchDetails;

public class GoalsOverUnderOdds extends Odds {

	private String overUnderOddsVariety;
	private String overUnderOddsProvider;
	private double overUnderOddsCourse;

	public String getOverUnderOddsVariety() {
		return overUnderOddsVariety;
	}

	public void setOverUnderOddsVariety(String overUnderOddsVariety) {
		this.overUnderOddsVariety = overUnderOddsVariety;
	}

	public String getOverUnderOddsProvider() {
		return overUnderOddsProvider;
	}

	public void setOverUnderOddsProvider(String overUnderOddsProvider) {
		this.overUnderOddsProvider = overUnderOddsProvider;
	}

	public double getOverUnderOddsCourse() {
		return overUnderOddsCourse;
	}

	public void setOverUnderOddsCourse(double overUnderOddsCourse) {
		this.overUnderOddsCourse = overUnderOddsCourse;
	}

}
