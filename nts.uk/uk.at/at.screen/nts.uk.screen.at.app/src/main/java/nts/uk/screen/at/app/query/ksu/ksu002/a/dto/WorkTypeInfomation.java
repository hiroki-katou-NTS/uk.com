package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import lombok.Value;

/**
 * 
 * @author chungnt <勤務種類, 必須任意不要区分, 出勤休日区分>を作る
 *
 */

@Value
public class WorkTypeInfomation {
	public WorkTypeDto workTypeDto;

	/**
	 * 必須である REQUIRED(0), 任意であるOPTIONAL(1), 不要であるNOT_REQUIRED(2);
	 */
	public int workTimeSetting;

	/**
	 * １日出勤系 FULL_TIME(3, "１日出勤系"), 午前出勤系 MORNING(1, "午前出勤系"), 午後出勤系 AFTERNOON(2,
	 * "午後出勤系"), １日休日系 HOLIDAY(0, "１日休日系");
	 */
	public int workStyle; // 出勤休日区分 attHdAtr;
}
