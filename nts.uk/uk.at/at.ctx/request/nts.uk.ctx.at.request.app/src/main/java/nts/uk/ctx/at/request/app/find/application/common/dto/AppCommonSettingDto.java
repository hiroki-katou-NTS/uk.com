package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppCommonSettingDto {
	public String generalDate;
	
	public ApplicationSettingDto applicationSettingDto;  
	
	// public ApprovalFunctionSettingDto approvalFunctionSettingDto;
	
	public List<AppTypeDiscreteSettingDto> appTypeDiscreteSettingDtos;  
	
	public List<ApplicationDeadlineDto> applicationDeadlineDtos;
	
	public static AppCommonSettingDto convertToDto(AppCommonSettingOutput output) {
		if(output == null) return null;
		AppCommonSettingDto appCommonSettingDto = new AppCommonSettingDto();
		appCommonSettingDto.generalDate = output.generalDate.toString();
		appCommonSettingDto.applicationSettingDto = ApplicationSettingDto.convertToDto(output.applicationSetting);
		// appCommonSettingDto.approvalFunctionSettingDto = ApprovalFunctionSettingDto.convertToDto(output.approvalFunctionSetting);
//		if(output.appTypeDiscreteSettings==null || output.appTypeDiscreteSettings.isEmpty()) {
//			appCommonSettingDto.appTypeDiscreteSettingDtos = null;
//		} else {
//			appCommonSettingDto.appTypeDiscreteSettingDtos = output.appTypeDiscreteSettings
//					.stream()
//					.map(x-> AppTypeDiscreteSettingDto.convertToDto(x))
//					.collect(Collectors.toList());
//		}
//		if(output.applicationDeadlines==null || output.applicationDeadlines.isEmpty()) {
//			appCommonSettingDto.applicationDeadlineDtos = null;
//		} else {
//			appCommonSettingDto.applicationDeadlineDtos = output.applicationDeadlines
//					.stream()
//					.map(x-> ApplicationDeadlineDto.convertToDto(x))
//					.collect(Collectors.toList());
//		}
		return appCommonSettingDto;
	}
}
