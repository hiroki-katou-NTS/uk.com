package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 申請制限設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppLimitSetting extends DomainObject {
	
	/**
	 * 実績修正がロック状態なら申請できない
	 */
	private Boolean canAppAchievementLock;
	
	/**
	 * 就業確定済の場合申請できない
	 */
	private Boolean canAppFinishWork;
	
	/**
	 * 日別実績が確認済なら申請できない
	 */
	private Boolean canAppAchievementConfirm;
	
	/**
	 * 時間外深夜の申請を利用する
	 */
	private Boolean canAppOTNight;
	
	/**
	 * 月別実績が確認済なら申請できない
	 */
	private Boolean canAppAchievementMonthConfirm;
	
	/**
	 * 申請理由が必須
	 */
	private Boolean requiredAppReason;
	
	public static AppLimitSetting toDomain(Integer canAppAchievementLock, 
			Integer canAppFinishWork, Integer canAppAchievementConfirm, 
			Integer canAppOTNight, Integer canAppAchievementMonthConfirm, 
			Integer requiredAppReason){
		return new AppLimitSetting(
				canAppAchievementLock == 1 ? true : false, 
				canAppFinishWork == 1 ? true : false, 
				canAppAchievementConfirm == 1 ? true : false, 
				canAppOTNight == 1 ? true : false, 
				canAppAchievementMonthConfirm == 1 ? true : false, 
				requiredAppReason == 1 ? true : false);
	}
	
}
