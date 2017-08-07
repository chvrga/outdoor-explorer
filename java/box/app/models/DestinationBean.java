package models;

import java.io.File;
import java.security.SecureRandom;

import org.omg.CORBA.UNKNOWN;

import play.Play;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.marklogic.client.pojo.annotation.Id;
import com.marklogic.client.pojo.annotation.PathIndexProperty;

/**
 * @author Ivana Frankic
 * <br><br>
 * Object representing destinations. Holds simple information about a destination itself. 
 * <br>
 * Users and destination interact through {@link ListBean}
 *	
 */
public class DestinationBean {
	/**
	 * path prefix for the file upload to the filesystem 
	 */
	public static final String logoPathPrefix 		=	"public" + File.separator + 
														"images" + File.separator + 
														"logos" + File.separator; 
												
	/**
	 * path prefix for the file upload to the filesystem
	 */
	public static final String backgroundPathPrefix = 	"public" + File.separator + 
														"images" + File.separator + 
														"backgrounds" + File.separator; 

	/**
	 * url prefix for displaying the file
	 */
	public static final String logoUrlPrefix 		= 	"public/images/logos/";											
	
	/**
	 * url prefix for displaying the file
	 */
	public static final String backgroundUrlPrefix 	= 	"public/images/backgrounds/"; 													
														
	private int id;
	private DestinationType type; 
	private String name;
	private String country;
	private String description;
	private String website;
	// Images
	private String logo;
	private String background;

	/**
	 * @param type {@link DestinationType}
	 * @param name name of this destination
	 * @param country country this destination is from
	 * @param description description of the destination
	 * @param website link, homepage
	 * @param logo filename 
	 * @param background filename
	 */
	public DestinationBean(DestinationType type, String name, String country, String description, String website, String logo, String background) {
		this.id = new SecureRandom().nextInt();
		this.type = type;
		this.name = name;
		this.country = country;
		this.description = description;
		this.website = website;
		this.logo = logoUrlPrefix + logo;
		this.background = backgroundUrlPrefix + background;
	}

	@Id
	@PathIndexProperty(scalarType = PathIndexProperty.ScalarType.INT)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DestinationType getType() {
		return type;
	}

	public void setType(DestinationType type) {
		this.type = type;
	}

	@PathIndexProperty(scalarType = PathIndexProperty.ScalarType.STRING)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@PathIndexProperty(scalarType = PathIndexProperty.ScalarType.STRING)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
	
	public enum DestinationType {
		UNKNOWN(-1),
		SKI(1),
		CITY(2),
		WILD(3);
		
		int type;
		private DestinationType(int i) {
			this.type = i;
		}
		
		public static DestinationType fromString(String type) {
			DestinationType result = UNKNOWN;
			switch (type) {
			case "ski":
			case "SKI":
				result = SKI;
				break;
			case "city":
			case "CITY":
				result = CITY;
				break;
			case "wild":
			case "WILD":
				result = WILD;
				break;
			default:
				break;
			}
			return result;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return " {"
				+"id:"+this.getId()
				+", type:" + this.getType()
				+", name:"+this.getName()
				+", country:"+this.getCountry()
				+"} ";
	}
}
