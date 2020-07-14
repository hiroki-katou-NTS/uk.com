package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdispset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDisplaySettingDto {
	/**
	 * 事前事後区分表示
	 */
	private int prePostDisplayAtr;
	
	/**
	 * 登録時の手動メール送信の初期値
	 */
	private int manualSendMailAtr;
	
	public static AppDisplaySettingDto fromDomain(AppDisplaySetting appDisplaySetting) {
		return new AppDisplaySettingDto(
				appDisplaySetting.getPrePostDisplayAtr().value, 
				appDisplaySetting.getManualSendMailAtr().value);
	}
	
	public AppDisplaySetting toDomain() {
		return new AppDisplaySetting(
				EnumAdaptor.valueOf(prePostDisplayAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(manualSendMailAtr, NotUseAtr.class));
	}
}
