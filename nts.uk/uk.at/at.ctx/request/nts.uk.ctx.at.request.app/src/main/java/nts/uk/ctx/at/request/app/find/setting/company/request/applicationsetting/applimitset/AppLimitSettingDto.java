package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.applimitset;

import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;

public class AppLimitSettingDto {
	/**
	 * 実績修正がロック状態なら申請できない
	 */
	public Boolean canAppAchievementLock;
	
	/**
	 * 就業確定済の場合申請できない
	 */
	public Boolean canAppFinishWork;
	
	/**
	 * 日別実績が確認済なら申請できない
	 */
	public Boolean canAppAchievementConfirm;
	
	/**
	 * 時間外深夜の申請を利用する
	 */
	public Boolean canAppOTNight;
	
	/**
	 * 月別実績が確認済なら申請できない
	 */
	public Boolean canAppAchievementMonthConfirm;
	
	/**
	 * 申請理由が必須
	 */
	public Boolean requiredAppReason;
	
	public static AppLimitSettingDto fromDomain(AppLimitSetting appLimitSetting) {
		AppLimitSettingDto appLimitSettingDto = new AppLimitSettingDto();
		appLimitSettingDto.canAppAchievementLock = appLimitSetting.getCanAppAchievementLock();
		appLimitSettingDto.canAppFinishWork = appLimitSetting.getCanAppFinishWork();
		appLimitSettingDto.canAppAchievementConfirm = appLimitSetting.getCanAppAchievementConfirm();
		appLimitSettingDto.canAppOTNight = appLimitSetting.getCanAppOTNight();
		appLimitSettingDto.canAppAchievementMonthConfirm = appLimitSetting.getCanAppAchievementMonthConfirm();
		appLimitSettingDto.requiredAppReason = appLimitSetting.getRequiredAppReason();
		return appLimitSettingDto;
	}
	
	public AppLimitSetting toDomain() {
		return new AppLimitSetting(
				canAppAchievementLock, 
				canAppFinishWork, 
				canAppAchievementConfirm, 
				canAppOTNight, 
				canAppAchievementMonthConfirm, 
				requiredAppReason);
	}
}
