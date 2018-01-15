package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class AppStampNewPreDto {
	
	public AppCommonSettingDto appCommonSettingDto;
	
	public AppStampSetDto appStampSetDto;
	
	public String companyID;
	
	public String employeeID;
	
	public String employeeName;
}
