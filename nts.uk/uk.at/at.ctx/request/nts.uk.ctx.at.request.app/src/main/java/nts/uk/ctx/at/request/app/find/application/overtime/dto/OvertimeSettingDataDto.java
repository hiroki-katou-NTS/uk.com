package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OvertimeSettingDataDto {
	public AppCommonSettingOutputDto appCommonSettingOutput;
	public AppOvertimeSettingDto appOvertimeSetting;
	public OvertimeRestAppCommonSettingDto overtimeRestAppCommonSet;
	
	public OvertimeSettingData toDomain() {
		OvertimeSettingData overtimeSettingData = new OvertimeSettingData();
		overtimeSettingData.appCommonSettingOutput = appCommonSettingOutput.toDomain();
		overtimeSettingData.appOvertimeSetting = appOvertimeSetting.toDomain();
		overtimeSettingData.overtimeRestAppCommonSet = overtimeRestAppCommonSet.toDomain();
		return overtimeSettingData; 
	}
}
