package service.matches.mapping;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LeaguesMappingProvider {

	private final Map<String, Integer> leaguesMapping = new HashMap<String, Integer>();

	public LeaguesMappingProvider() {
		fillLeaguesMapping();
	}

	public Map<String, Integer> getLeaguesMapping() {
		return leaguesMapping;
	}

	private void fillLeaguesMapping() {
		leaguesMapping.put("SOCGERBL1", 351);
		leaguesMapping.put("SOCGERBL2", 352);
		leaguesMapping.put("SOCENGPRE", 354);
		leaguesMapping.put("SOCFRAONE", 355);
		leaguesMapping.put("SOCFRATWO", 356);
		leaguesMapping.put("SOCITASEA", 357);
		leaguesMapping.put("SOCESPPRI", 358);
		leaguesMapping.put("SOCESPSEG", 359);
		leaguesMapping.put("SOCNLDERE", 360);
		leaguesMapping.put("SOCITASEB", 361);
		leaguesMapping.put("SOCINTCHL", 362);
		leaguesMapping.put("SOCARGPRI", 368);
	}

}
