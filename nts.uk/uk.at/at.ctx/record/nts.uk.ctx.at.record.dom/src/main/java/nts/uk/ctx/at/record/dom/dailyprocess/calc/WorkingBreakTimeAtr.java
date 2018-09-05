package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 勤務間休憩区分
 * @author keisuke_hoshina
 *
 */
public enum WorkingBreakTimeAtr {
	WORKING,
	NOTWORKING;
	
	/**
	 * 勤務中であるか判定する
	 * @return　勤務中である
	 */
	public boolean isWorking() {
		return this.equals(WORKING);
	}
	
	/**
	 * 勤務中ではないか判定する
	 * @return　勤務中ではないである。
	 */
	public boolean isNotWorking() {
		return this.equals(NOTWORKING);
	}
}
