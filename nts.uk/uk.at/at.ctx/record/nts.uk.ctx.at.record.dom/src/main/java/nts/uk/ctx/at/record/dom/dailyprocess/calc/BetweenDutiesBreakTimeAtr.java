package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 勤務間休憩区分
 * @author ken_takasu
 *
 */
public enum BetweenDutiesBreakTimeAtr {
	
	BETWEEN_DUTIES,//勤務間
	NOT_BETWEEN_DUTIES;//勤務間ではない
	
	/**
	 * 勤務間であるか判定する
	 * @return　勤務間である
	 */
	public boolean isBetweenDuties() {
		return BETWEEN_DUTIES.equals(this);
	}
	
}
