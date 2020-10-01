package nts.uk.ctx.at.function.dom.adapter.worklocation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkLocationImport {

	private String companyID;
	
	private String workLocationCD;
	
	private String workLocationName;
	
	private String horiDistance;
	
	private String vertiDistance;
	
	private String latitude;
	
	private String longitude;

	public WorkLocationImport(String companyID, String workLocationCD, String workLocationName, String horiDistance,
			String vertiDistance, String latitude, String longitude) {
		this.companyID = companyID;
		this.workLocationCD = workLocationCD;
		this.workLocationName = workLocationName;
		this.horiDistance = horiDistance;
		this.vertiDistance = vertiDistance;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}
