/**
 * 5:08:45 PM Nov 8, 2017
 */
package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *条件式の属性
 */
@AllArgsConstructor
public enum ConditionAtr {

	/** 回数 */
	TIMES(0, "Enum_ConditionAtr_Times"),
	/** 時間 */
	TIME_DURATION(1, "Enum_ConditionAtr_TimeDuration"),
	/** 時刻 */
	TIME_WITH_DAY(2, "Enum_ConditionAtr_TimeWithDay"),
	/** 金額 */
	AMOUNT_VALUE(3, "Enum_ConditionAtr_AmountValue");

	public final int value;

	public final String nameId;
	
}
