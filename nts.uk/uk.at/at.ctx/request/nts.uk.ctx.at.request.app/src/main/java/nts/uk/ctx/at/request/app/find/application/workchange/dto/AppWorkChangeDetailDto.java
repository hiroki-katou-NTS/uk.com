package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto_Old;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDetailOutput;

@AllArgsConstructor
@NoArgsConstructor
public class AppWorkChangeDetailDto {
	
	/**
	 * 勤務変更申請の表示情報
	 */
	public AppWorkChangeDispInfoDto_Old appWorkChangeDispInfo;
	
	/**
	 * 勤務変更申請
	 */
	public AppWorkChangeDto_Old appWorkChange;
	
	public static AppWorkChangeDetailDto fromDomain(AppWorkChangeDetailOutput output) {
		AppWorkChangeDetailDto result = new AppWorkChangeDetailDto();
		result.appWorkChangeDispInfo = AppWorkChangeDispInfoDto_Old.fromDomain(output.getAppWorkChangeDispInfo());
		result.appWorkChange = AppWorkChangeDto_Old.fromDomain(output.getAppWorkChange());
		return result;
	}
	
}
