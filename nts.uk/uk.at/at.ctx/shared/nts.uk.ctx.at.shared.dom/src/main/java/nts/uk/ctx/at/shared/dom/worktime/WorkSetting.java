package nts.uk.ctx.at.shared.dom.worktime;

import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;

/**
 * 勤務設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.勤務設定
 *
 * @author kumiko_otake
 *
 */
public interface WorkSetting {

	/**
	 * 変更可能な勤務時間帯を取得する
	 * @param require Require
	 * @return 変更可能な時間帯
	 */
	public ChangeableWorkingTimeZone getChangeableWorkingTimeZone(Require require);

	/**
	 * 休憩時間帯を取得する
	 * @param isWorkingOnDayOff 休出か
	 * @param amPmAtr 午前午後区分
	 * @return 休憩時間
	 */
	public BreakTimeZone getBreakTimeZone(boolean isWorkingOnDayOff, AmPmAtr amPmAtr);


	public static interface Require {
	}

}
