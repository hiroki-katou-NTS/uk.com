package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OutputAllDataApp;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSet;

@AllArgsConstructor
@Value
public class GoBackDirectDetailDto {
	//直行直帰
	GoBackDirectlyDto goBackDirectlyDto;
	//事前事後区分
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
	//AppCommonSettingDto appCommonSetting;
	DetailedScreenPreBootModeDto detailedScreenPreBootModeDto;
	
	PrelaunchAppSettingDto prelaunchAppSettingDto;

	ApplicationDto appDto;
	
	//OutputAllDataApp outputAllDataApp;

	int OutMode;
	/**
	 * get Data of GoBackDirect with Application Setting
	 * 
	 * @param domain
	 * @return
	 */
	public static GoBackDirectDetailDto convertToDto(GoBackDirectAppSet domain) {
		return new GoBackDirectDetailDto(
				GoBackDirectlyDto.convertToDto(
				domain.getGoBackDirectly()),
				domain.getPrePostAtr(), 
				domain.getWorkLocationName1(), 
				domain.getWorkLocationName2(),
				domain.getWorkTypeName(), 
				domain.getWorkTimeName(), 
				domain.getAppReasonId(), 
				domain.getAppReason(),
				domain.getAppDate(), 
				DetailedScreenPreBootModeDto.convertToDto(domain.getDetailedScreenPreBootModeOutput()),
				PrelaunchAppSettingDto.convertToDto(domain.getPrelaunchAppSetting()),
				ApplicationDto.fromDomain(domain.getApplication()),
				domain.getDetailScreenInitModeOutput().getOutputMode().value
				);
	}
}
