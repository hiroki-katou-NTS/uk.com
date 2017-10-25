package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

/**
 * 休憩種類
 * 
 * @author keisuke_hoshina
 *
 */
public enum BreakType {
	ReferenceFromWorkTime,
	ReferenceFromSchedule,
	;
	public BreakType is() {
		return this;
	}
	
	/**
	 * 就業時間帯から参照であるか判定する
	 * @return　就業時間帯から参照である
	 */
	public boolean isReferenceFromWorkTime() { 
		return ReferenceFromWorkTime.equals(this);
	}
	
	/**
	 * スケジュールから参照であるか判定する
	 * @return スケジュールから参照　である
	 */
	public boolean isReferenceFromSchedule() {
		return ReferenceFromSchedule.equals(this);
	}
}
