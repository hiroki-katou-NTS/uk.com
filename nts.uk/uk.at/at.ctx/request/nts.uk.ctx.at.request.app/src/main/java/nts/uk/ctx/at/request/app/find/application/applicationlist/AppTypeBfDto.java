package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppTypeBfDto {
	public List<BfReqSetDto> beforeAfter;
	public List<AppTypeSetDto> appType;
	
	public static AppTypeBfDto convertToDto(RequestSetting domain){
		return new AppTypeBfDto(BfReqSetDto.convertToDto(domain), AppTypeSetDto.convertToDto(domain));
	}
}
