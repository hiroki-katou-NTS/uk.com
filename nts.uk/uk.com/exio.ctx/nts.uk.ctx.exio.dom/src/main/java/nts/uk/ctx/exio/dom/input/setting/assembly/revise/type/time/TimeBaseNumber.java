package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 時間・時刻進数
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TimeBaseNumber {
	
	/** 60進数 */
	SEXAGESIMAL(0, "Enum_TimeBaseNumber_SEXAGESIMAL"), 
	
	/** 10進数 */
	DECIMAL(1, "Enum_TimeBaseNumber_DECIMAL");
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	public static TimeBaseNumber valueOf(int value) {
		return EnumAdaptor.valueOf(value, TimeBaseNumber.class);
	}
}
