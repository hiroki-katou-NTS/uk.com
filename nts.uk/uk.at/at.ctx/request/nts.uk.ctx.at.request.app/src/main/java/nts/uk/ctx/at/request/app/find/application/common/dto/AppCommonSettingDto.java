package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.requestofearch.RequestOfEachCommonDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingDto;

@AllArgsConstructor
@NoArgsConstructor
public class AppCommonSettingDto {
	public String generalDate;
	
	public ApplicationSettingDto applicationSettingDto;  
	
	public RequestOfEachCommonDto requestOfEachCommonDtos;
	
	public List<AppTypeDiscreteSettingDto> appTypeDiscreteSettingDtos;  
	
	public List<ApplicationDeadlineDto> applicationDeadlineDtos;
}
