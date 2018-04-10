/**
 * 11:19:40 AM Nov 9, 2017
 */
package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
//条件値の種別
@AllArgsConstructor
public enum ConditionType {

	/* 固定値 */
	FIXED_VALUE(0, "Enum_ConditionType_FixedValue"),
	/* 勤怠項目 */
	ATTENDANCE_ITEM(1, "Enum_ConditionType_AttendanceItem");

	public final int value;

	public final String nameId;
	
}
