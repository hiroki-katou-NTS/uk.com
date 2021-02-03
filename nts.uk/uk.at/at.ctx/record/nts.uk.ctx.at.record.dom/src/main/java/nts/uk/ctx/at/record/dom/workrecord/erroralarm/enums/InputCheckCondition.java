package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author HungTT - 入力チェック条件
 *
 */

@RequiredArgsConstructor
public enum InputCheckCondition {

	/** 入力されていない */
	INPUT_NOT_DONE(0, "Enum_ConditionType_FixedValue"),
	/** 入力されている */
	INPUT_DONE(1, "Enum_ConditionType_AttendanceItem");

	public final int value;
	public final String nameId;

	public static InputCheckCondition of(int value) {
		return EnumAdaptor.valueOf(value, InputCheckCondition.class);
	}
	
}
