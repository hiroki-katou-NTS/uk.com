package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetDto;
import nts.uk.shr.com.context.AppContexts;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OvertimeSettingDataDto {
	public AppCommonSettingOutputDto appCommonSettingOutput;
	public OvertimeAppSetDto appOvertimeSetting;
	public OvertimeRestAppCommonSettingDto overtimeRestAppCommonSet;
	
	public OvertimeSettingData toDomain() {
		OvertimeSettingData overtimeSettingData = new OvertimeSettingData();
		overtimeSettingData.appCommonSettingOutput = appCommonSettingOutput.toDomain();
		overtimeSettingData.appOvertimeSetting = appOvertimeSetting.toDomain(AppContexts.user().companyId());
		overtimeSettingData.overtimeRestAppCommonSet = overtimeRestAppCommonSet.toDomain();
		return overtimeSettingData; 
	}
}
