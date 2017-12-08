package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.DetailedScreenPreBootModeDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.PrelaunchAppSettingDto;
import nts.uk.ctx.at.request.dom.application.workchange.WorkChangeDetail;

@AllArgsConstructor
@Value
public class WorkChangeDetailDto {
	/**
	* 勤務変更申請
	*/
	AppWorkChangeDto workChangeDto;
	/**
	 * 申請
	 */
	ApplicationDto applicationDto;
	/**
	 * 申請者名
	 */
	private String employeeName;
	/**
	 * 申請者社員ID
	 */
	private String sID;
		
	DetailedScreenPreBootModeDto detailedScreenPreBootMode;

	PrelaunchAppSettingDto prelaunchAppSetting;
	
	public static WorkChangeDetailDto  formDomain(WorkChangeDetail domain){
		return new WorkChangeDetailDto(AppWorkChangeDto.fromDomain(domain.getAppWorkChange()), 
				ApplicationDto.fromDomain(domain.getApplication()), 
				domain.getEmployeeName(), domain.getSID(), 
				DetailedScreenPreBootModeDto.convertToDto(domain.getDetailedScreenPreBootModeOutput()),
				PrelaunchAppSettingDto.convertToDto(domain.getPrelaunchAppSetting()));
	}
}
