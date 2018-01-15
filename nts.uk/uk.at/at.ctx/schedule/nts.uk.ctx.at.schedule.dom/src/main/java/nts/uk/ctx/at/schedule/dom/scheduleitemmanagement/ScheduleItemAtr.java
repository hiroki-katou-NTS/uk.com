package nts.uk.ctx.at.schedule.dom.scheduleitemmanagement;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScheduleItemAtr {
	/** 0- 時間 **/
	TIME(0),
	/** 1- 時刻 **/
	TIME_OF_DAY(1),
	/** 2- 回数 **/
	TIMES_NUMBER(2),
	/** 3- 区分 **/
	CLASSIFICATION(3),
	/** 4- コード **/
	CODE(4),
	/** 5- 文字 **/
	CHARACTER(5),
	/** 6- 金額 **/
	TOTAL_MONEY(6);
	
	public final int value;
}
