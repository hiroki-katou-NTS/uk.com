package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.displaysetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.AppDisplaySetting;

@AllArgsConstructor
@NoArgsConstructor
public class AppDisplaySettingDto {
	
	/**
	 * 事前事後区分表示
	 */
	public int prePostAtrDisp;
	
	/**
	 * 就業時間帯の検索
	 */
	public int searchWorkingHours;
	
	/**
	 * 登録時の手動メール送信の初期値
	 */
	public int manualSendMailAtr;
	
	public static AppDisplaySettingDto fromDomain(AppDisplaySetting appDisplaySetting) {
		AppDisplaySettingDto appDisplaySettingDto = new AppDisplaySettingDto();
		appDisplaySettingDto.prePostAtrDisp = appDisplaySetting.getPrePostAtrDisp().value;
		appDisplaySettingDto.searchWorkingHours = appDisplaySetting.getSearchWorkingHours().value;
		appDisplaySettingDto.manualSendMailAtr = appDisplaySetting.getManualSendMailAtr().value;
		return appDisplaySettingDto;
	}
}
