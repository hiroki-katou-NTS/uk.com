package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;

@NoArgsConstructor
@Data
public class OvertimeSettingData {
	public AppCommonSettingOutput appCommonSettingOutput;
	public OvertimeAppSet appOvertimeSetting;
	public OvertimeRestAppCommonSetting overtimeRestAppCommonSet;
}
