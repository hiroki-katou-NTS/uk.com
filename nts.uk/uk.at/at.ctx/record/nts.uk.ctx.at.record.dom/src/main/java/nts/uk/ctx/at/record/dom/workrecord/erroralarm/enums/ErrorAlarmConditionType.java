package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author HungTT - エラーアラーム条件の種別
 *
 */

@RequiredArgsConstructor
public enum ErrorAlarmConditionType {

	/** 固定値 */
	FIXED_VALUE(0, "Enum_ConditionType_FixedValue"),
	/** 勤怠項目 */
	ATTENDANCE_ITEM(1, "Enum_ConditionType_AttendanceItem"),
	/** 入力チェック */
	INPUT_CHECK(2, "Enum_ConditionType_InputCheck");

	public final int value;
	public final String nameId;

	public static ErrorAlarmConditionType of(int value) {
		return EnumAdaptor.valueOf(value, ErrorAlarmConditionType.class);
	}
	
}
