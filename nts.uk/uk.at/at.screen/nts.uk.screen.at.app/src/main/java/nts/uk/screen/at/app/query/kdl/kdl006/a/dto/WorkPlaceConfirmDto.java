package nts.uk.screen.at.app.query.kdl.kdl006.a.dto;

import java.util.Optional;

import nts.arc.time.GeneralDateTime;

public class WorkPlaceConfirmDto {

	public String workPlaceId;
	
	public String workPlaceName;
	
	public Boolean confirmEmployment;
	
	public String confirmEmployeeId;
	
	public String confirmEmployeeName;
	
	public String confirmationTime;

	public WorkPlaceConfirmDto(String workPlaceId, String workPlaceName, Boolean confirmEmployment,
			String confirmEmployeeId, String confirmEmployeeName, Optional<GeneralDateTime> confirmationTime) {
		super();
		this.workPlaceId = workPlaceId;
		this.workPlaceName = workPlaceName;
		this.confirmEmployment = confirmEmployment;
		this.confirmEmployeeId = confirmEmployeeId;
		this.confirmEmployeeName = confirmEmployeeName;
		this.confirmationTime = confirmationTime.isPresent() ? confirmationTime.get().toString("yyyy/MM/dd HH:mm") : null;
	}
	
}
