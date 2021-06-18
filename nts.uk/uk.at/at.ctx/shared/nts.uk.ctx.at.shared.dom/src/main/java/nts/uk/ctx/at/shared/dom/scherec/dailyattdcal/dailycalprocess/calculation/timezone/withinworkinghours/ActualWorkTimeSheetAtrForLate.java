package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

/**
 * 実働時間帯区分（遅刻早退時間帯用）
 * @author shuichi_ishida
 */
public enum ActualWorkTimeSheetAtrForLate {
	Late,		//　遅刻
	LeaveEarly;	// 早退
	
	/**
	 * 遅刻かどうか判定する
	 * @return 遅刻ならtrue
	 */
	public boolean isLate() {
		return this.equals(Late);
	}
	
	/**
	 * 早退かどうか判定する
	 * @return 早退ならtrue
	 */
	public boolean isLeaveEarly() {
		return this.equals(LeaveEarly);
	}
}
