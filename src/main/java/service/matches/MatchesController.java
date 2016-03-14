package service.matches;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import service.matches.data.in.event.ReceivedEvent;
import service.matches.data.in.event.odds.ReceivedOddsCourse;
import service.matches.data.in.event.odds.ReceivedOddsCourseWrapper;
import service.matches.data.in.event.odds.ReceivedOddsValueWrapper;
import service.matches.data.in.fixture.ReceivedFixture;
import service.matches.data.in.fixture.ReceivedFixtures;
import service.matches.data.in.fixture.ReceivedFixturesWrapper;
import service.matches.data.in.league.ReceivedLeague;
import service.matches.data.in.league.ReceivedLeagueWrapper;
import service.matches.data.in.score.ReceivedScoreWrapper;
import service.matches.data.in.score.ReceivedScoresWrapper;
import service.matches.data.out.league.League;
import service.matches.data.out.match.Match;
import service.matches.data.out.matchDetails.GoalsOverUnderOdds;
import service.matches.data.out.matchDetails.MatchDetails;
import service.matches.data.out.matchDetails.Odds;
import service.matches.data.out.matchDetails.ThreeWayOdds;
import service.matches.data.out.score.Score;
import service.matches.mapping.LeaguesMappingProvider;

@RestController
public class MatchesController {

	// TODO exceptions when illegal params

	@Autowired
	LeaguesMappingProvider leaguesMappingProvider;

	private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

	@RequestMapping("/leagues")
	public List<League> leagues() {

		RestTemplate restTemplate = new RestTemplate();
		ReceivedLeagueWrapper receivedLeagues = restTemplate.getForObject("http://api.odds24.com/partner/leagues?user=dev", ReceivedLeagueWrapper.class);

		List<League> leagues = new ArrayList<League>();
		for (Entry<String, ReceivedLeague> receivedLeague : receivedLeagues.entrySet()) {
			League league = new League();
			league.setCode(receivedLeague.getKey());
			league.setName(receivedLeague.getValue().getName());
			league.setFixturesLink(createLink("matches/fixtures", "leagueName", receivedLeague.getKey()));
			league.setScoresLink(createLink("matches/scores", "leagueName", receivedLeague.getKey()));
			leagues.add(league);
		}

		return leagues;
	}

	@RequestMapping("/matches/fixtures")
	public Object matchesFixtures(@RequestParam(value = "leagueName") String leagueName) {
		if (leagueName == null || leagueName.isEmpty()) {
			return Collections.emptyList();
		} else {
			RestTemplate restTemplate = new RestTemplate();
			ReceivedFixturesWrapper receivedFixturesWrapper =
					restTemplate.getForObject("http://api.odds24.com/fixtures?user=dev&leagues=" + leagueName, ReceivedFixturesWrapper.class);

			List<Match> matches = new ArrayList<Match>();
			for (Entry<String, ReceivedFixtures> receivedFixtures : receivedFixturesWrapper.entrySet()) {
				for (ReceivedFixture receivedFixture : receivedFixtures.getValue().getFixtures()) {
					Match match = new Match();
					match.setHomeTeam(receivedFixture.getHomeTeam());
					match.setAwayTeam(receivedFixture.getAwayTeam());
					match.setDate(dateFormat.format(new Date(Long.valueOf(receivedFixture.getTime() + "000"))));
					match.setDetailsLink(createLink("match/details", "matchEvent", receivedFixture.getEventLink()));
					matches.add(match);
				}
			}
			return matches;
		}
	}

	@RequestMapping("/match/details")
	public MatchDetails matchDetails(@RequestParam(value = "matchEvent") String matchEvent) {
		if (matchEvent == null || matchEvent.isEmpty()) {
			return null;
		} else {
			RestTemplate restTemplate = new RestTemplate();
			ReceivedEvent receivedEvent =
					restTemplate.getForObject("http://api.odds24.com/odds/event/" + matchEvent + "?user=dev", ReceivedEvent.class);

			List<Odds> bestOdds = new ArrayList<Odds>();
			ThreeWayOdds threeWayOdds = getBestThreeWayOdds(receivedEvent);
			if (threeWayOdds != null) {
				bestOdds.add(threeWayOdds);
			}
			List<GoalsOverUnderOdds> goalsOverUnderOdds = getBestOverUnderGoalsOdds(receivedEvent);
			if (goalsOverUnderOdds != null) {
				bestOdds.addAll(goalsOverUnderOdds);
			}

			MatchDetails matchDetails = new MatchDetails();
			matchDetails.setHomeTeam(receivedEvent.getHomeTeam());
			matchDetails.setAwayTeam(receivedEvent.getAwayTeam());
			matchDetails.setTime(dateFormat.format(new Date(Long.valueOf(receivedEvent.getTime() + "000"))));
			matchDetails.setBestOdds(bestOdds);

			return matchDetails;
		}
	}

	private ThreeWayOdds getBestThreeWayOdds(ReceivedEvent receivedEvent) {
		if (receivedEvent.getOdds() != null && receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
					!= null && receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY).get(ReceivedOddsValueWrapper.BEST) != null) {
			ThreeWayOdds odds = new ThreeWayOdds();
			if (receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY).get(ReceivedOddsValueWrapper.BEST)
					.get(ReceivedOddsCourseWrapper.HOME) != null) {
				odds.setHomeOddsProvider(receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
						.get(ReceivedOddsValueWrapper.BEST).get(ReceivedOddsCourseWrapper.HOME).getProviderName());
				odds.setHomeOddsCourse(receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
						.get(ReceivedOddsValueWrapper.BEST).get(ReceivedOddsCourseWrapper.HOME).getOddsCourse());
			}
			if (receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY).get(ReceivedOddsValueWrapper.BEST)
					.get(ReceivedOddsCourseWrapper.DRAW) != null) {
				odds.setDrawOddsProvider(receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
						.get(ReceivedOddsValueWrapper.BEST).get(ReceivedOddsCourseWrapper.DRAW).getProviderName());
				odds.setDrawOddsCourse(receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
						.get(ReceivedOddsValueWrapper.BEST).get(ReceivedOddsCourseWrapper.DRAW).getOddsCourse());
			}
			if (receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY).get(ReceivedOddsValueWrapper.BEST)
					.get(ReceivedOddsCourseWrapper.AWAY) != null) {
				odds.setAwayOddsProvider(receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
						.get(ReceivedOddsValueWrapper.BEST).get(ReceivedOddsCourseWrapper.AWAY).getProviderName());
				odds.setAwayOddsCourse(receivedEvent.getOdds().get(ReceivedOddsValueWrapper.THREE_WAY)
						.get(ReceivedOddsValueWrapper.BEST).get(ReceivedOddsCourseWrapper.AWAY).getOddsCourse());
			}
			return odds;
		}
		return null;
	}

	private List<GoalsOverUnderOdds> getBestOverUnderGoalsOdds(ReceivedEvent receivedEvent) {
		if (receivedEvent.getOdds() != null && receivedEvent.getOdds().get(ReceivedOddsValueWrapper.OVER_UNDER_GOALS)
					!= null && receivedEvent.getOdds().get(ReceivedOddsValueWrapper.OVER_UNDER_GOALS).get(ReceivedOddsValueWrapper.BEST) != null) {
			List<GoalsOverUnderOdds> overUnderOdds = new ArrayList<GoalsOverUnderOdds>();
			for (Entry<String, ReceivedOddsCourse> entry : receivedEvent.getOdds().get(ReceivedOddsValueWrapper.OVER_UNDER_GOALS).get(ReceivedOddsValueWrapper.BEST).entrySet()) {
				GoalsOverUnderOdds odds = new GoalsOverUnderOdds();
				odds.setOverUnderOddsVariety(entry.getKey());
				odds.setOverUnderOddsProvider(entry.getValue().getProviderName());
				odds.setOverUnderOddsCourse(entry.getValue().getOddsCourse());
				overUnderOdds.add(odds);
			}
			return overUnderOdds;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/matches/scores")
	public List<Score> matchesScores(@RequestParam(value = "leagueName") String leagueName, @RequestParam(required = false, value = "matchDay") Integer matchDay) {
		if (leagueName == null || leagueName.isEmpty() || leaguesMappingProvider.getLeaguesMapping().get(leagueName) == null) {
			return null;
		} else {
			RestTemplate restTemplate = new RestTemplate();
			ReceivedScoresWrapper receivedScoresWrapper = null;

			if (matchDay == null) {
				receivedScoresWrapper = restTemplate.getForObject("http://api.football-data.org/alpha/soccerseasons/"
						+ leaguesMappingProvider.getLeaguesMapping().get(leagueName) + "/fixtures", ReceivedScoresWrapper.class);
			} else {
				receivedScoresWrapper = restTemplate.getForObject("http://api.football-data.org/alpha/soccerseasons/"
						+ leaguesMappingProvider.getLeaguesMapping().get(leagueName) + "/fixtures?matchday=" + matchDay, ReceivedScoresWrapper.class);
			}

			if (receivedScoresWrapper != null) {
				if (receivedScoresWrapper.get(ReceivedScoresWrapper.FIXTURES) != null
						&& receivedScoresWrapper.get(ReceivedScoresWrapper.FIXTURES) instanceof List<?>) {
					List<Score> scores = new ArrayList<Score>();
					List<LinkedHashMap<Object, Object>> receivedScoreWrappers = (List<LinkedHashMap<Object, Object>>) receivedScoresWrapper.get(ReceivedScoresWrapper.FIXTURES);
					for (LinkedHashMap<Object, Object> linkedHashMap : receivedScoreWrappers) {
						Score score = new Score();
						score.setHomeTeam((String) linkedHashMap.get(ReceivedScoreWrapper.HOME_TEAM));
						score.setAwayTeam((String) linkedHashMap.get(ReceivedScoreWrapper.AWAY_TEAM));
						if (linkedHashMap.get(ReceivedScoreWrapper.RESULT) instanceof Map<?, ?>) {
							Map<String, Integer> resultMap = (Map<String, Integer>) linkedHashMap.get(ReceivedScoreWrapper.RESULT);
							score.setHomeTeamGoals((Integer) resultMap.get(ReceivedScoreWrapper.RESULT_HOME_GOALS));
							score.setAwayTeamGoals((Integer) resultMap.get(ReceivedScoreWrapper.RESULT_AWAY_GOALS));
						}
						score.setDate((String) linkedHashMap.get(ReceivedScoreWrapper.DATE));
						score.setMatchDay((Integer) linkedHashMap.get(ReceivedScoreWrapper.MATCHDAY));
						scores.add(score);
					}
					return scores;
				}
			}
		}
		return null;
	}

	@ExceptionHandler(RestClientException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody
	String handleException(RestClientException e) {
		return "Response contains no data! " + e.getMessage();
	}

	private String createLink(String prefix, String paramName, String paramValue) {
		return "http://localhost:8080/" + prefix + "?" + paramName + "=" + paramValue;
	}

}
