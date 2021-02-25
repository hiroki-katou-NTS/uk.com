package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime;
/**
 * 休憩種別
 * @author keisuke_hoshina
 *
 */
public enum BreakClassification {
	BREAK_STAMP, //休憩打刻
	BREAK;       //休憩

	/**
	 * 休憩打刻であるか判定する
	 * @return　休憩打刻である
	 */
	public boolean isBreakStamp() {
		return BREAK_STAMP.equals(this);
	}
	
	/**
	 * 休憩であるか判定する
	 * @return　休憩である
	 */
	public boolean isBreak() {
		return BREAK.equals(this);
	}
}

