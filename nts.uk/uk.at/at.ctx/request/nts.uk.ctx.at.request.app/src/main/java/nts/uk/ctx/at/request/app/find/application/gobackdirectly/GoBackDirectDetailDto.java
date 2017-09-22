package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSet;

@AllArgsConstructor
@Value
public class GoBackDirectDetailDto {
	GoBackDirectlyDto goBackDirectlyDto;
	int prePostAtr;
	String workLocationName1;
	String workLocationName2;
	String workTypeName;
	String workTimeName;
	String appReasonId;
	String appReason;
	String appDate;
	AppCommonSettingDto appCommonSetting;

	/**
	 * get Data of GoBackDirect with Application Setting
	 * 
	 * @param domain
	 * @return
	 */
	public static GoBackDirectDetailDto convertToDto(GoBackDirectAppSet domain) {
		return new GoBackDirectDetailDto(GoBackDirectlyDto.convertToDto(domain.getGoBackDirectly()),
				domain.getPrePostAtr(), domain.getWorkLocationName1(), domain.getWorkLocationName2(),
				domain.getWorkTypeName(), domain.getWorkTimeName(), domain.getAppReasonId(), domain.getAppReason(),
				domain.getAppDate(), AppCommonSettingDto.convertToDto(domain.getAppCommonSetOut()));
	}
}
