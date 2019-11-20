package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingDto;

@Data
@AllArgsConstructor
public class AppCommonSettingOutputDto {
	public GeneralDate generalDate;

	public ApplicationSettingDto applicationSetting;

	public ApprovalFunctionSettingDto approvalFunctionSetting;

	public List<AppTypeDiscreteSettingDto> appTypeDiscreteSettings;

	public List<ApplicationDeadlineDto> applicationDeadlines;
	/**
	 * 雇用別申請承認設定
	 */
	public List<AppEmploymentSettingDto> appEmploymentWorkType;

}
