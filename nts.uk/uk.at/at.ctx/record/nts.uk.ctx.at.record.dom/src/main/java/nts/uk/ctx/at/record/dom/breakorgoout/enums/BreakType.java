package nts.uk.ctx.at.record.dom.breakorgoout.enums;

import lombok.AllArgsConstructor;
/**
 * 
 * @author nampt
 * 休憩種類
 *
 */
@AllArgsConstructor
public enum BreakType {
	
	/* 就業時間帯から参照 */
	REFER_WORK_TIME(0),
	/* スケジュールから参照 */
	REFER_SCHEDULE(1);
	
	public final int value;
	
	/**
	 * 就業時間帯から参照であるか判定する
	 * @return　就業時間帯から参照である
	 */
	public boolean isReferWorkTime() {
		return REFER_WORK_TIME.equals(this);
	}
	
	/**
	 * スケジュールから参照であるか判定する
	 * @return スケジュールから参照である
	 */
	public boolean isReferSchedule() {
		return REFER_SCHEDULE.equals(this);
	}
}
