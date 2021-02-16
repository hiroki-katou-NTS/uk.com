package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import nts.uk.shr.com.permit.AvailabilityPermissionBase;

/**
 * スケジュール修正個人別の権限制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.権限制御.スケジュール修正個人別の権限制御
 * @author dan_pv
 *
 */
public class ScheModifyAuthCtrlByPerson extends AvailabilityPermissionBase {

	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @param functionNo 機能NO
	 * @param isAvailable 利用できる
	 */
	public ScheModifyAuthCtrlByPerson(String companyId, String roleId, int functionNo, boolean isAvailable) {
		super(companyId, roleId, functionNo, isAvailable);
	}

}
