package nts.uk.ctx.at.record.app.find.worklocation;

import lombok.Value;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;

@Value
/**
 * 
 * @author hieult
 *
 */
public class WorkLocationDto {
	
	private String companyID;
		
	private String workLocationCD;
	
	private String workLocationName;
	
	private String horiDistance;
	
	private String vertiDistance;
	
	private String latitude;
	
	private String longitude;
	
	public static WorkLocationDto fromDomain (WorkLocation domain) {
		return new WorkLocationDto (
				domain.getCompanyID(),
				domain.getWorkLocationCD().v(),
				domain.getWorkLocationName().v(),
				domain.getHoriDistance(),
				domain.getVertiDistance(),
				domain.getLatitude().v(),
				domain.getLongitude().v()
				);
	}

}
