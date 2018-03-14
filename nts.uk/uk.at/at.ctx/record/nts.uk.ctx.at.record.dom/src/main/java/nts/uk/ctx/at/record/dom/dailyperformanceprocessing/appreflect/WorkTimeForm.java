package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;
/**
 * 就業時間帯の勤務形態
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum WorkTimeForm {
	/**固定勤務 */
	FIXED(0, "固定勤務"),
	/**	フレックス勤務 */
	FLEX(1, "フレックス勤務"),
	/**流動勤務	 */
	FLOW(2,"流動勤務"),
	/**	時差勤務 */
	TIMEDIFFERENCE(3, "時差勤務");
	
	public final Integer value;
	
	public final String name;
}
