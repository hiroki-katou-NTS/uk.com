package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;

@Data
@AllArgsConstructor
public class AppCommonSettingOutputDto {
	public String generalDate;

	public ApplicationSettingDto applicationSetting;

	// public ApprovalFunctionSettingDto approvalFunctionSetting;

	public List<AppTypeDiscreteSettingDto> appTypeDiscreteSettings;

	public List<ApplicationDeadlineDto> applicationDeadlines;
	/**
	 * 雇用別申請承認設定
	 */
	public AppEmploymentSettingDto appEmploymentWorkType;
	
	public AppCommonSettingOutput toDomain() {
		AppCommonSettingOutput appCommonSettingOutput = new AppCommonSettingOutput();
		appCommonSettingOutput.generalDate = GeneralDate.fromString(generalDate, "yyyy/MM/dd");
		appCommonSettingOutput.applicationSetting = applicationSetting.toDomain();
		// appCommonSettingOutput.approvalFunctionSetting = ApprovalFunctionSettingDto.createFromJavaType(approvalFunctionSetting);
		// appCommonSettingOutput.appTypeDiscreteSettings = appTypeDiscreteSettings.stream().map(x -> x.toDomain()).collect(Collectors.toList());
//		appCommonSettingOutput.applicationDeadlines = applicationDeadlines.stream()
//				.map(x -> ApplicationDeadline.createSimpleFromJavaType(x.companyId, x.closureId, x.userAtr, x.deadline, x.deadlineCriteria)).collect(Collectors.toList());
		appCommonSettingOutput.appEmploymentWorkType = appEmploymentWorkType == null ? Optional.empty() : appEmploymentWorkType.toDomainOptional();
		return appCommonSettingOutput;
	}

}
