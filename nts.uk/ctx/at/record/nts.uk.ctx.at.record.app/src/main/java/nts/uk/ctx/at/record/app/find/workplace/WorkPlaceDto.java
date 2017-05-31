package nts.uk.ctx.at.record.app.find.workplace;

import lombok.Value;
import workplace.WorkPlace;

@Value
/**
 * 
 * @author hieult
 *
 */
public class WorkPlaceDto {
	
	private String companyID;
		
	private String workLocationCD;
	
	private String workLocationName;
	
	private String horiDistance;
	
	private String vertiDistance;
	
	private String latitude;
	
	private String longitude;
	
	public static WorkPlaceDto fromDomain (WorkPlace domain) {
		return new WorkPlaceDto (
				domain.getCompanyID(),
				domain.getWorkLocationCD(),
				domain.getWorkLocationName(),
				domain.getHoriDistance(),
				domain.getVertiDistance(),
				domain.getLatitude().v(),
				domain.getLongitude().v()
				);
	}

}
