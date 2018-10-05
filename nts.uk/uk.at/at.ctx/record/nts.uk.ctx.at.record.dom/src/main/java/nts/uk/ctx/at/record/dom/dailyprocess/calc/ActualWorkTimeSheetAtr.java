package nts.uk.ctx.at.record.dom.dailyprocess.calc;
/**
 * 実働時間帯区分
 * @author keisuke_hoshina
 *
 */
public enum ActualWorkTimeSheetAtr {
	WithinWorkTime,//就業時間内
	OverTimeWork,//残業
	EarlyWork,//早出残業
	StatutoryOverTimeWork,//法定内残業
	HolidayWork;//休出
	
	/**
	 * 就内時間であるか判定する
	 * @return　就業時間である
	 */
	public boolean isWithinWorkTime() {
		return this.equals(WithinWorkTime);
	}
}
