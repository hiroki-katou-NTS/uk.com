package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import lombok.Value;

/**
 * 休憩種類
 * 
 * @author keisuke_hoshina
 *
 */
public enum BreakCategory {
	ReferenceFromWorkTime,
	ReferenceFromSchedule,
	ReferenceFromBreakStamp
	;
	public BreakCategory is() {
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
	
	/**
	 * 休憩打刻から参照であるか判定する
	 * @return　休憩打刻から参照である
	 */
	public boolean isReferenceFromBreakStamp() {
		return ReferenceFromBreakStamp.equals(this);
	}
}
