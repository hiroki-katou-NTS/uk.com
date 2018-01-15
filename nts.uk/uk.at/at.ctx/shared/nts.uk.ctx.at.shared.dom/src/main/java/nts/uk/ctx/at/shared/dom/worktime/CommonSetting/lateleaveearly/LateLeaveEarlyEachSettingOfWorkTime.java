package nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearly;

import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearlysetting.LateLeaveEarlyClassification;

/**
 * 就業時間帯の遅刻・早退別設定
 * @author keisuke_hoshina
 *
 */
public class LateLeaveEarlyEachSettingOfWorkTime {
	private LateLeaveEarlyClassification lateLeaveEarlyClassification;
	private GraceTimeSetting graceTimeSetting;
	private boolean isCalculatedAtHolidayWork; 
	
	/**
	 * パラメータが遅刻早退区分と一致しているか返す
	 * @param t　遅刻早退区分
	 * @return　一致している場合：true　一致していない場合：false
	 */
	public boolean lateLeaveEarlyDecision(LateLeaveEarlyClassification t) {
		
		return this.lateLeaveEarlyClassification.equals(t);
	}
}
