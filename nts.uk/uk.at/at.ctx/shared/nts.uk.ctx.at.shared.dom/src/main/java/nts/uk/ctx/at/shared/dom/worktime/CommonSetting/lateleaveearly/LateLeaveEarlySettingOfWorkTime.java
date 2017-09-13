package nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly;

import lombok.Value;

/**
 * 就業時間帯の遅刻・早退設定
 * @author ken_takasu
 *
 */
@Value
public class LateLeaveEarlySettingOfWorkTime {

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



