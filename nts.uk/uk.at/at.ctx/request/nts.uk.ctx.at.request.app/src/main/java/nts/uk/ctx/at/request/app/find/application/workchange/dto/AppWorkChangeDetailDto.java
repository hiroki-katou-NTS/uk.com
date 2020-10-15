package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDetailOutput;

public class AppWorkChangeDetailDto {
	/**
	 * 勤務変更申請の表示情報
	 */
	public AppWorkChangeDispInfoDto appWorkChangeDispInfo;
	
	/**
	 * 勤務変更申請
	 */
	public AppWorkChangeDto appWorkChange;
	
	public static AppWorkChangeDetailDto fromDomain(AppWorkChangeDetailOutput output) {
		AppWorkChangeDetailDto result = new AppWorkChangeDetailDto();
		result.appWorkChangeDispInfo = AppWorkChangeDispInfoDto.fromDomain(output.getAppWorkChangeDispInfo());
		result.appWorkChange = AppWorkChangeDto.fromDomain(output.getAppWorkChange());
		return result;
	}
}
