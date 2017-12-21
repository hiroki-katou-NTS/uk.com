package nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearlysetting;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * 就業時間帯の遅刻・早退別設定
 * @author keisuke_hoshina
 *
 */
public class LateLeaveEarlyEachSettingOfWorkTime {
	private LateLeaveEarlyClassification lateLeaveEarlyClassification;
	private GraceTimeSetting graceTimeSetting;
	private boolean isCalculatedAtHolidayWork;
	private TimeRoundingSetting forRecordTimeRoundingSetting;
	private TimeRoundingSetting forDeductionTimeRoundingSetting;
	
	
	/**
	 * パラメータが遅刻早退区分と一致しているか返す
	 * @param t　遅刻早退区分
	 * @return　一致している場合：true　一致していない場合：false
	 */
	public boolean lateLeaveEarlyDecision(LateLeaveEarlyClassification t) {
		
		return this.lateLeaveEarlyClassification.equals(t);
	}
	
//	/**
//	 * 指定された控除区分に対応する丸め設定を返す
//	 * @param category
//	 * @return　控除区分＝控除の場合：控除時間丸め設定　控除区分＝計上の場合：時間丸め
//	 */
//	public TimeRoundingSetting getTimeRoundingSetting(DeductionAtr category) {
//		if(category.isDeduction()) {
//			return this.forDeductionTimeRoundingSetting;
//		}else {
//			return this.forRecordTimeRoundingSetting;
//		}
//	}
}
