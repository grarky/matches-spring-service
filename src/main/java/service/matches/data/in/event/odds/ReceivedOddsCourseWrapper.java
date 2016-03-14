package service.matches.data.in.event.odds;

import java.util.LinkedHashMap;

public class ReceivedOddsCourseWrapper extends LinkedHashMap<String, ReceivedOddsCourse> {

	private static final long serialVersionUID = 812524434915777099L;

	// three way
	public final static String HOME = "h";
	public final static String AWAY = "a";
	public final static String DRAW = "d";

	// over under
	public final static String NAME = "b";
	public final static String COURSE = "o";

}
