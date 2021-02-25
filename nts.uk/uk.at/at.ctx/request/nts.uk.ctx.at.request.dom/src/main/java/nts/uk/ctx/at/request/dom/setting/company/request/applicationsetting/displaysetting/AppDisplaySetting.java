package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 申請表示設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppDisplaySetting extends DomainObject {
	
	/**
	 * 事前事後区分表示
	 */
	private DisplayAtr prePostAtrDisp;
	
	/**
	 * 就業時間帯の検索
	 */
	private UseAtr searchWorkingHours;
	
	/**
	 * 登録時の手動メール送信の初期値
	 */
	private NotUseAtr manualSendMailAtr;
	
	public static AppDisplaySetting toDomain(Integer prePostAtr, Integer searchWorkingHours, Integer manualSendMailAtr){
		return new AppDisplaySetting(
				EnumAdaptor.valueOf(prePostAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(searchWorkingHours, UseAtr.class), 
				EnumAdaptor.valueOf(manualSendMailAtr, NotUseAtr.class));
	}
	
}
