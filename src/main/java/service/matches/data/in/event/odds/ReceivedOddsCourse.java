package service.matches.data.in.event.odds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedOddsCourse {

	@JsonProperty("b")
	private String providerName;

	@JsonProperty("o")
	private double oddsCourse;

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public double getOddsCourse() {
		return oddsCourse;
	}

	public void setOddsCourse(double oddsCourse) {
		this.oddsCourse = oddsCourse;
	}

}
