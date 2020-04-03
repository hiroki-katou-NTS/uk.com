package nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.AppReflectAfterConfirm;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ReflectAtr;

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
	
	public AppReflectAfterConfirm toDomain() {
		return new AppReflectAfterConfirm(
				EnumAdaptor.valueOf(scheduleConfirmedAtr, ReflectAtr.class), 
				EnumAdaptor.valueOf(achievementConfirmedAtr, ReflectAtr.class));
	}
}
