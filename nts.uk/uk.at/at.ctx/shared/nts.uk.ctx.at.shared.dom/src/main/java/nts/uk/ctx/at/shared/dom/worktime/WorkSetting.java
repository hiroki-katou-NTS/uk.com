package nts.uk.ctx.at.shared.dom.worktime;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * 勤務設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.勤務設定
 *
 * @author kumiko_otake
 *
 */
public interface WorkSetting {

	/** 会社ID */
	public String getCompanyId();
	
	/** 就業時間帯コード */
	public WorkTimeCode getWorkTimeCode();

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

	/**
	 * 所定時間設定を取得する
	 * @param require Require
	 * @param wktmCd 就業時間帯コード
	 * @return 所定時間設定
	 */
	default Optional<PredetemineTimeSetting> getPredetermineTimeSetting(Require require) {
		return require.predetemineTimeSetting(this.getCompanyId(), this.getWorkTimeCode());
	}

	public static interface Require extends PredetemineTimeSetting.Require {
		
	}
}
