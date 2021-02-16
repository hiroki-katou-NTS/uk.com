package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import nts.uk.shr.com.permit.DescriptionOfAvailabilityPermissionBase;

/**
 * スケジュール修正共通の機能
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.権限制御.スケジュール修正共通の機能
 * @author dan_pv
 *
 */
public class ScheModifyFuncCommon extends DescriptionOfAvailabilityPermissionBase {

	/**
	 * @param functionNo No
	 * @param name 表示名
	 * @param explanation 説明文
	 * @param displayOrder 表示順
	 * @param defaultValue 初期値
	 */
	public ScheModifyFuncCommon(int functionNo, String name, String explanation, int displayOrder,
			boolean defaultValue) {
		super(functionNo, name, explanation, displayOrder, defaultValue);
	}

}
