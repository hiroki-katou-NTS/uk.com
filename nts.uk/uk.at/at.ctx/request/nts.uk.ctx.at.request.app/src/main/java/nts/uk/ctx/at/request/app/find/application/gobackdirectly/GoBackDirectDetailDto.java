package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSet;

@AllArgsConstructor
@Value
public class GoBackDirectDetailDto {
	//直行直帰
	GoBackDirectlyDto_Old goBackDirectlyDto;
	//	
	int prePostAtr;
	//場所１
	String workLocationName1;
	//場所１
	String workLocationName2;
	//勤務種類
	String workTypeName;
	//就業時間帯
	String workTimeName;
	//定型理由
	String appReasonId;
	//申請理由
	String appReason;
	//申請日
	String appDate;
	
	String employeeName;
	//AppCommonSettingDto appCommonSetting;
	DetailedScreenPreBootModeDto detailedScreenPreBootModeDto;
	
	PrelaunchAppSettingDto prelaunchAppSettingDto;
	
	int OutMode;
	
	/**
	 * get Data of GoBackDirect with Application Setting
	 * 
	 * @param domain
	 * @return
	 */
	public static GoBackDirectDetailDto convertToDto(GoBackDirectAppSet domain) {
		return new GoBackDirectDetailDto(
				GoBackDirectlyDto_Old.convertToDto(
				domain.getGoBackDirectly()),
				domain.getPrePostAtr(), 
				domain.getWorkLocationName1(), 
				domain.getWorkLocationName2(),
				domain.getWorkTypeName(), 
				domain.getWorkTimeName(), 
				domain.getAppReasonId(), 
				domain.getAppReason(),
				domain.getAppDate(), 
				domain.getEmployeeName(),
				DetailedScreenPreBootModeDto.convertToDto(domain.getDetailedScreenPreBootModeOutput()),
				PrelaunchAppSettingDto.convertToDto(domain.getPrelaunchAppSetting()),
				domain.getDetailScreenInitModeOutput().getOutputMode().value
				);
	}
}
