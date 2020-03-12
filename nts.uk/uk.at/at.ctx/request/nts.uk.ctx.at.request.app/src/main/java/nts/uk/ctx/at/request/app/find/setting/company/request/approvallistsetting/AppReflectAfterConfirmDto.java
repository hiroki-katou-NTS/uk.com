package nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting;

import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.AppReflectAfterConfirm;

public class AppReflectAfterConfirmDto {
	/**
	 * スケジュールが確定されている場合
	 */
	public int scheduleConfirmedAtr;
	
	/**
	 * 実績が確定されている場合
	 */
	public int achievementConfirmedAtr;
	
	public static AppReflectAfterConfirmDto fromDomain(AppReflectAfterConfirm appReflectAfterConfirm) {
		AppReflectAfterConfirmDto appReflectAfterConfirmDto = new AppReflectAfterConfirmDto();
		appReflectAfterConfirmDto.scheduleConfirmedAtr = appReflectAfterConfirm.getScheduleConfirmedAtr().value;
		appReflectAfterConfirmDto.achievementConfirmedAtr = appReflectAfterConfirm.getAchievementConfirmedAtr().value;
		return appReflectAfterConfirmDto;
	}
}
