package models;

import java.security.SecureRandom;

import com.marklogic.client.pojo.annotation.Id;
import com.marklogic.client.pojo.annotation.PathIndexProperty;

/**
 * @author Ivana Frankic
 * <br><br>
 * This is created to make it a separate json sub-part of the object for access from the view 
 */
public class SlopeCountsBean {
	private Integer id;
	private Integer difficult;
	private Integer medium;
	private Integer easy;
	private Integer freeride;
	
	/**
	 * @param difficult number of black slopes
	 * @param medium number of red slopes
	 * @param easy number of blue slopes
	 * @param freeride number of freeride slopes
	 */
	public SlopeCountsBean(Integer difficult, Integer medium, Integer easy, Integer freeride) {
		this.id = new SecureRandom().nextInt();
		this.difficult = difficult;
		this.medium = medium;
		this.easy = easy;
		this.freeride = freeride;
	}

	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@PathIndexProperty(scalarType=PathIndexProperty.ScalarType.INT)
	public Integer getDifficult() {
		return difficult;
	}

	public void setDifficult(Integer diffcult) {
		this.difficult = diffcult;
	}

	@PathIndexProperty(scalarType=PathIndexProperty.ScalarType.INT)
	public Integer getMedium() {
		return medium;
	}

	public void setMedium(Integer medium) {
		this.medium = medium;
	}

	@PathIndexProperty(scalarType=PathIndexProperty.ScalarType.INT)
	public Integer getEasy() {
		return easy;
	}

	public void setEasy(Integer easy) {
		this.easy = easy;
	}

	@PathIndexProperty(scalarType=PathIndexProperty.ScalarType.INT)
	public Integer getFreeride() {
		return freeride;
	}

	public void setFreeride(Integer freeride) {
		this.freeride = freeride;
	}
}
