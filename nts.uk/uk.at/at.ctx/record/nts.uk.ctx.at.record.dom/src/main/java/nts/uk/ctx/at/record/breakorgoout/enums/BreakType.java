package nts.uk.ctx.at.record.breakorgoout.enums;

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

}
