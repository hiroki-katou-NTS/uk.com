package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;

/**
 * 
 * @author hieult
 *
 */
@Value
public class ScreenLateOrLeaveEarlyDto {

	private LateOrLeaveEarlyDto lateOrLeaveEarlyDto;
	
	/** 定型理由 typicalReason :DB reasonTemp */ 
	private List<ApplicationReasonDto> ListApplicationReasonDto;
	
	private String employeeID;
	
	private String applicantName;
	
	private AppCommonSettingDto appCommonSettingDto;
	
	private WorkManagementMultipleDto workManagementMultiple; 

}