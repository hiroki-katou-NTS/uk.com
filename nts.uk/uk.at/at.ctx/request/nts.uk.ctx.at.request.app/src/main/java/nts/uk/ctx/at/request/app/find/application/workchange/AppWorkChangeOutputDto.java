package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfo_NewDto;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeOutput;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeOutputDto {
//	勤務変更申請の表示情報
	private AppWorkChangeDispInfo_NewDto appWorkChangeDispInfo;
//	勤務変更申請＜Optional＞
	private AppWorkChange_NewDto appWorkChange;
	
	public static AppWorkChangeOutputDto fromDomain(AppWorkChangeOutput appWorkChangeOutput) {
		AppWorkChangeOutputDto appWorkChangeOutputDto = new AppWorkChangeOutputDto();
		appWorkChangeOutputDto.setAppWorkChangeDispInfo(AppWorkChangeDispInfo_NewDto.fromDomain(appWorkChangeOutput.getAppWorkChangeDispInfo()));
		appWorkChangeOutputDto.setAppWorkChange(AppWorkChange_NewDto.fromDomain(appWorkChangeOutput.getAppWorkChange()));
		return appWorkChangeOutputDto;
	}
}
