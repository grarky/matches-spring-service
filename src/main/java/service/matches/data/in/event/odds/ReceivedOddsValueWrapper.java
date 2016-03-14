package service.matches.data.in.event.odds;

import java.util.LinkedHashMap;

public class ReceivedOddsValueWrapper extends LinkedHashMap<String, ReceivedOddsCourseWrapper> {

	private static final long serialVersionUID = 1782557418985023633L;

	// odds categories
	public final static String THREE_WAY = "3w";
	public final static String OVER_UNDER_GOALS = "ouft";

	// odds types
	public final static String BEST = "best";
	public final static String ALL = "all";
}
