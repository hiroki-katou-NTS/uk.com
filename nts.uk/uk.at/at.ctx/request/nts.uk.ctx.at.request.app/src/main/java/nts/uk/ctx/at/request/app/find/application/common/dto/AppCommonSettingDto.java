package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.requestofearch.RequestOfEachCommonDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;

@AllArgsConstructor
@NoArgsConstructor
public class AppCommonSettingDto {
	public String generalDate;
	
	public ApplicationSettingDto applicationSettingDto;  
	
	public RequestOfEachCommonDto requestOfEachCommonDtos;
	
	public List<AppTypeDiscreteSettingDto> appTypeDiscreteSettingDtos;  
	
	public List<ApplicationDeadlineDto> applicationDeadlineDtos;
	
	public static AppCommonSettingDto convertToDto(AppCommonSettingOutput output) {
		return new AppCommonSettingDto(
				output.generalDate.toString(), 
				ApplicationSettingDto.convertToDto(output.applicationSetting),
				RequestOfEachCommonDto.convertToDto(output.requestOfEachCommon),
				output.appTypeDiscreteSettings
					.stream()
					.map(x-> AppTypeDiscreteSettingDto.convertToDto(x))
					.collect(Collectors.toList()),
				output.applicationDeadlines
					.stream()
					.map(x-> ApplicationDeadlineDto.convertToDto(x))
					.collect(Collectors.toList())
				);
	}
}
