package nts.uk.ctx.at.record.dom.dailyprocess.calc;
/**
 * 実働時間帯区分
 * @author keisuke_hoshina
 *
 */
public enum ActualWorkTimeSheetAtr {
	WithinWorkTime,
	OverTimeWork,
	EarlyWork,
	StatutoryOverTimeWork,
	HolidayWork;
	
	/**
	 * 就内時間であるか判定する
	 * @return　就業時間である
	 */
	public boolean isWithinWorkTime() {
		return this.equals(WithinWorkTime);
	}
}
