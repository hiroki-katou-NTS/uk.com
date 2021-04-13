package nts.uk.ctx.at.function.dom.adapter.worklocation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkLocationImport {

	private String contractCode;
	
	private String workLocationCD;
	
	private String workLocationName;
	
	/**
	 * bỏ dài rộng, thay bằng bán kính (Enum Radius)
	 */
	private int radius;
	/**
	 * 
	 */
	private double latitude;
	/**
	 * 
	 */
	private double longitude;
	public WorkLocationImport(String contractCode, String workLocationCD, String workLocationName, int radius,
			double latitude, double longitude) {
		super();
		this.contractCode = contractCode;
		this.workLocationCD = workLocationCD;
		this.workLocationName = workLocationName;
		this.radius = radius;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	
}
