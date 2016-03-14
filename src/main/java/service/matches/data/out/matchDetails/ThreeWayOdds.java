package service.matches.data.out.matchDetails;

public class ThreeWayOdds extends Odds {

	private String homeOddsProvider;
	private double homeOddsCourse;

	private String drawOddsProvider;
	private double drawOddsCourse;

	private String awayOddsProvider;
	private double awayOddsCourse;

	public String getHomeOddsProvider() {
		return homeOddsProvider;
	}

	public void setHomeOddsProvider(String homeOddsProvider) {
		this.homeOddsProvider = homeOddsProvider;
	}

	public double getHomeOddsCourse() {
		return homeOddsCourse;
	}

	public void setHomeOddsCourse(double homeOddsCourse) {
		this.homeOddsCourse = homeOddsCourse;
	}

	public String getDrawOddsProvider() {
		return drawOddsProvider;
	}

	public void setDrawOddsProvider(String drawOddsProvider) {
		this.drawOddsProvider = drawOddsProvider;
	}

	public double getDrawOddsCourse() {
		return drawOddsCourse;
	}

	public void setDrawOddsCourse(double drawOddsCourse) {
		this.drawOddsCourse = drawOddsCourse;
	}

	public String getAwayOddsProvider() {
		return awayOddsProvider;
	}

	public void setAwayOddsProvider(String awayOddsProvider) {
		this.awayOddsProvider = awayOddsProvider;
	}

	public double getAwayOddsCourse() {
		return awayOddsCourse;
	}

	public void setAwayOddsCourse(double awayOddsCourse) {
		this.awayOddsCourse = awayOddsCourse;
	}
	
}
