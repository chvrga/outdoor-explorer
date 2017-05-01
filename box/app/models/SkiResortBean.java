package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.marklogic.client.pojo.annotation.Id;
import com.marklogic.client.pojo.annotation.PathIndexProperty;

/**
 * @author Ivana Frankic
 * <br><br>
 * A {@link DestinationBean} + some ski specific information
 */
public class SkiResortBean extends DestinationBean{
	private int altitude;

	// Slope counts for the bar chart
	private SlopeCountsBean slopeCounts;

	// Slope length in kilometers
	private int slopeLength;

	/**
	 * Same as in {@link DestinationBean}
	 * @param name name of this destination
	 * @param country country this destination is from
	 * @param description description of the destination
	 * @param website link, homepage
	 * @param logo filename 
	 * @param background filename
	 * <br> 
	 * @param altitude altitude of the highest peak (meters)
	 * @param slopeLength kilometers of slopes
	 * @param difficult number of black slopes
	 * @param medium number of red slopes
	 * @param easy number of blue slopes
	 * @param freeride number of freeride slopes
	 */
	public SkiResortBean(String name, String country, String description, String website, String logo, String background, int altitude, int slopeLength, int difficult, int medium, int easy, int freeride) {
		super(DestinationType.SKI, name, country, description, website, logo, background);
		this.altitude = altitude;
		this.slopeLength = slopeLength;

		this.slopeCounts = new SlopeCountsBean(difficult, medium, easy, freeride);
	}

	@PathIndexProperty(scalarType = PathIndexProperty.ScalarType.INT)
	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public SlopeCountsBean getSlopeCounts() {
		return slopeCounts;
	}

	public void setSlopeCounts(SlopeCountsBean slopeCounts) {
		this.slopeCounts = slopeCounts;
	}

	@PathIndexProperty(scalarType = PathIndexProperty.ScalarType.INT)
	public int getSlopeLength() {
		return slopeLength;
	}

	public void setSlopeLength(int slopeLength) {
		this.slopeLength = slopeLength;
	}
}
