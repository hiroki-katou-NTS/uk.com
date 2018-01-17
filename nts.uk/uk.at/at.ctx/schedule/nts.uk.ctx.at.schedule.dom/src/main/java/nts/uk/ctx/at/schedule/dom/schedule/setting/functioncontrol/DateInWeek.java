package nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;

/**
 * 曜日
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
public enum DateInWeek {
	/* 月曜日 */
	MONDAY(0),
	/* 火曜日 */
	TUESDAY(1),
	/* 水曜日 */
	WEDNESDAY(2),
	/* 木曜日 */
	THURSDAY(3),
	/* 金曜日 */
	FRIDAY(4),
	/* 土曜日 */
	SATURDAY(5),
	/* 日曜日 */
	SUNDAY(6);
	
	public final int value;
}
